package com.the.movie.db.source.remote

import com.the.movie.db.mapper.toTvWithTvRecommendation
import com.the.movie.db.source.remote.network.services.TvApiService
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.TvResponse
import com.the.movie.db.source.remote.response.base.IRecommendation
import com.the.movie.db.source.remote.response.model.TvResult
import javax.inject.Inject

class RemoteTvDataSource @Inject constructor(
    private val apiService: TvApiService
) : RemoteBaseDataSource<TvResult, TvResponse>() {

    public override suspend fun getDiscover(page: Int) =
        getPage { apiService.getDiscoverTv(page = page) }

    override suspend fun getRecommendation(id: Int, page: Int) =
        apiService.getRecommendationTv(id, page)

    override suspend fun getResponse(id: Int) = apiService.getTv(id)

    override fun <T : TvResponse> toWithRecommendation(
        dataResponse: T, dataRecommendations: List<PageResponse<TvResult>>
    ): IRecommendation<TvResult> = dataResponse.toTvWithTvRecommendation(dataRecommendations)
}