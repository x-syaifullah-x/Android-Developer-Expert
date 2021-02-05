package com.the.movie.db.source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.the.movie.db.source.local.base.IResultEntity
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.base.IResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import timber.log.Timber

@ExperimentalPagingApi
abstract class DiscoverRemoteMediator<Key : Any, LocalResult : IResultEntity, RemoteResult : IResult> :
    RemoteMediator<Key, LocalResult>() {

    companion object {
        const val STARTING_PAGE = 1
        const val MIN_PAGE_INDEX_SIZE = 20
        const val MAX_PAGE_SIZE = 5
    }

    final override suspend fun load(
        loadType: LoadType,
        state: PagingState<Key, LocalResult>
    ): MediatorResult {

        val firstLoaded = state.isEmpty()
        val page = when (loadType) {
            LoadType.REFRESH -> if (firstLoaded) STARTING_PAGE else nextPage() ?: STARTING_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> nextPage()
                ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        if (page > MAX_PAGE_SIZE) return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val results = withContext(Dispatchers.IO) {
                val pageSize = state.config.pageSize
                if (pageSize < MIN_PAGE_INDEX_SIZE) throw MediatorPageThrowable("minimum pageSize $MIN_PAGE_INDEX_SIZE")
                if (pageSize % MIN_PAGE_INDEX_SIZE != 0) {
                    val message =
                        "pageSize ${20.rangeTo(MAX_PAGE_SIZE * 20).filter { it % 20 == 0 }}"
                    throw MediatorPageThrowable(message)
                }
                List(pageSize / MIN_PAGE_INDEX_SIZE) {
                    async {
                        val currentPage = if (it == 0) page else page.plus(1)
                        when (val result = fetch(currentPage)) {
                            is ApiResponse.Success -> result.data
                            is ApiResponse.Empty -> null
                            is ApiResponse.Error -> throw Throwable(result.throwable)
                        }
                    }
                }.run { awaitAll(*toTypedArray()) }
            }

            if (loadType == LoadType.REFRESH && firstLoaded) clearDiscover()

            var endOfPaginationReached = true
            results.forEach {
                it?.apply {
                    endOfPaginationReached = it.results.isEmpty()
                    withContext(Dispatchers.IO) { saveFetch(it) }
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (t: MediatorPageThrowable) {
            Timber.e(t)
            throw IllegalArgumentException(t.message)
        } catch (t: Throwable) {
            Timber.e(t)
            MediatorResult.Error(t)
        }
    }

    abstract suspend fun nextPage(): Int?

    abstract suspend fun fetch(page: Int): ApiResponse<PageResponse<RemoteResult>>

    abstract suspend fun clearDiscover(): Int

    abstract suspend fun saveFetch(pageResponse: PageResponse<RemoteResult>): Boolean
}