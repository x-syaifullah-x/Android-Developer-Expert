package com.the.movie.db.source.remote.network.services

import com.the.movie.db.BuildConfig
import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getDiscoverMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): PageResponse<MovieResult>

    @GET("3/movie/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("language") language: String = "en-US",
    ): MovieResponse?

    @GET("3/movie/{id}/recommendations?api_key=${BuildConfig.API_KEY}")
    suspend fun getRecommendationMovie(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
    ): PageResponse<MovieResult>
}