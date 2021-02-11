package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.base.IRecommendation
import com.the.movie.db.source.remote.response.base.IResponse
import com.the.movie.db.source.remote.response.base.IResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.Int.Companion.MAX_VALUE

abstract class RemoteBaseDataSource<Result : IResult, Response : IResponse> {

    protected suspend fun getPage(pageResponse: suspend () -> PageResponse<Result>) =
        ApiResponse.fetch {
            val fetch = pageResponse()
            if (fetch.results.isEmpty()) ApiResponse.Empty else ApiResponse.Success(fetch)
        }

    protected abstract suspend fun getDiscover(page: Int): ApiResponse<PageResponse<Result>>

    protected abstract suspend fun getRecommendation(id: Int, page: Int): PageResponse<Result>

    protected abstract suspend fun getResponse(id: Int): Response?

    protected abstract fun <T : Response> toWithRecommendation(
        dataResponse: T, dataRecommendations: List<PageResponse<Result>>
    ): IRecommendation<Result>

    private suspend fun getPages(
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
            .filterIsInstance<ApiResponse.Success<PageResponse<Result>>>()
            .map { it.data }
    }

    suspend fun <T : IRecommendation<Result>> getResponseWithRecommendation(id: Int) = flow {
        val result = withContext(Dispatchers.IO) {
            val response = async { getResponse(id) }
            val recommendation = async {
                getPages { page -> getRecommendation(id, page) }
            }
            awaitAll(response, recommendation)
        }

        @Suppress("UNCHECKED_CAST")
        if (result[0] == null) {
            emit(ApiResponse.Empty)
        } else {
            val response = result[0] as Response
            val recommendations = result[1] as List<PageResponse<Result>>
            val data = toWithRecommendation(response, recommendations) as T
            emit(ApiResponse.Success(data))
        }
    }.catch { emit(ApiResponse.Error(it)) }.flowOn(Dispatchers.IO)
}