package com.the.movie.db.source.remote

import com.the.movie.db.mapper.toTvWithTvRecommendation
import com.the.movie.db.source.remote.network.ApiResponse
import com.the.movie.db.source.remote.network.services.TvApiService
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.TvResponse
import com.the.movie.db.source.remote.response.model.TvResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteTvDataSource @Inject constructor(
    private val apiService: TvApiService
) : RemoteBaseDataSource<TvResult>() {

    public override suspend fun getDiscover(page: Int) =
        getPage { apiService.getDiscoverTv(page = page) }

    @Suppress("UNCHECKED_CAST")
    suspend fun getTvWithTvRecommendation(id: Int) = flow {
        val result = withContext(Dispatchers.IO) {
            val tv = async { apiService.getTv(id) }
            val recommendation = async {
                getPages { page -> apiService.getRecommendationTv(id, page) }
            }
            awaitAll(tv, recommendation)
        }

        val tvResult = result[0]
        val recommendationTvResult = result[1] as List<PageResponse<TvResult>>
        if (tvResult is TvResponse) {
            val movieWithTvRecommendation =
                tvResult.toTvWithTvRecommendation(recommendationTvResult)
            emit(ApiResponse.Success(movieWithTvRecommendation))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { emit(ApiResponse.Error(it)) }.flowOn(Dispatchers.IO)
}