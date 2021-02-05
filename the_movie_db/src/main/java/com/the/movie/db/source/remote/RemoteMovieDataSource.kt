package com.the.movie.db.source.remote

import com.the.movie.db.mapper.toMovieWithRecommendation
import com.the.movie.db.source.remote.network.ApiResponse
import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val apiService: MovieApiService
) : RemoteBaseDataSource<MovieResult>() {

    public override suspend fun getDiscover(page: Int) =
        getPage { apiService.getDiscoverMovie(page = page) }

    @Suppress("UNCHECKED_CAST")
    suspend fun getMovieWithMovieRecommendation(id: Int) = flow {
        val result = withContext(Dispatchers.IO) {
            val movie = async { apiService.getMovie(id) }
            val recommendation = async {
                getPages { page -> apiService.getRecommendationMovie(id, page) }
            }
            awaitAll(movie, recommendation)
        }

        val movieResult = result[0]
        val recommendationMovieResult = result[1] as List<PageResponse<MovieResult>>
        if (movieResult is MovieResponse) {
            val movieWithMovieRecommendation =
                movieResult.toMovieWithRecommendation(recommendationMovieResult)
            emit(ApiResponse.Success(movieWithMovieRecommendation))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { emit(ApiResponse.Error(it)) }.flowOn(Dispatchers.IO)
}