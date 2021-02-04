package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.network.ApiResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.base.IResult
import com.the.movie.db.source.remote.response.model.MovieResult
import kotlinx.coroutines.*
import kotlin.Int.Companion.MAX_VALUE

abstract class RemoteBaseDataSource<Result : IResult> {
    protected suspend fun getPage(pageResponse: suspend () -> PageResponse<Result>) =
        ApiResponse.fetch {
            val fetch = pageResponse()
            if (fetch.results.isEmpty()) ApiResponse.Empty else ApiResponse.Success(fetch)
        }

    protected abstract suspend fun getDiscover(page: Int): ApiResponse<PageResponse<Result>>

    protected suspend fun getPages(
        maxPage: Int = MAX_VALUE, fetch: suspend (page: Int) -> PageResponse<Result>
    ) = coroutineScope {
        val firstPage = withContext(Dispatchers.IO) { getPage { fetch(1) } }
        val results = mutableListOf<ApiResponse<PageResponse<*>>>()
        if (firstPage is ApiResponse.Success) {
            results.add(firstPage)
            val pageTwo = firstPage.data.page + 1
            val endPage =
                if (firstPage.data.totalPages > maxPage) maxPage else firstPage.data.totalPages
            val nextPage = pageTwo.rangeTo(endPage)
                .map { page -> async { getPage { fetch(page) } } }.toTypedArray()
            if (nextPage.isNotEmpty()) awaitAll(*nextPage)
                .map { results.add(it) }
                .flatMap { results }
        }
        return@coroutineScope results.toList()
            .filterIsInstance<ApiResponse.Success<PageResponse<MovieResult>>>()
            .map { it.data }
    }
}