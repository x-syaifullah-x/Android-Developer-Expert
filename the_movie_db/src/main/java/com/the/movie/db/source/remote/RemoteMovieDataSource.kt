package com.the.movie.db.source.remote

import com.the.movie.db.mapper.toMovieWithRecommendation
import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.base.IRecommendation
import com.the.movie.db.source.remote.response.model.MovieResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val apiService: MovieApiService
) : RemoteBaseDataSource<MovieResult, MovieResponse>() {

    public override suspend fun getDiscover(page: Int) =
        getPage { apiService.getDiscoverMovie(page = page) }

    override suspend fun getRecommendation(id: Int, page: Int) =
        apiService.getRecommendationMovie(id, page)

    override suspend fun getResponse(id: Int) = apiService.getMovie(id)

    override fun <T : MovieResponse> toWithRecommendation(
        dataResponse: T, dataRecommendations: List<PageResponse<MovieResult>>
    ): IRecommendation<MovieResult> = dataResponse.toMovieWithRecommendation(dataRecommendations)
}