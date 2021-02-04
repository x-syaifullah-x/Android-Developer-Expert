package com.the.movie.db.source.remote.network

import com.the.movie.db.BuildConfig.API_KEY
import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.TvResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import com.the.movie.db.source.remote.response.model.MultiResult
import com.the.movie.db.source.remote.response.model.TvResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/discover/movie?api_key=$API_KEY")
    suspend fun getDiscoverMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): PageResponse<MovieResult>

    @GET("3/movie/{id}?api_key=${API_KEY}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("language") language: String = "en-US",
    ): MovieResponse?

    @GET("3/tv/{id}?api_key=${API_KEY}")
    suspend fun getTv(
        @Path("id") id: Int,
        @Query("language") language: String = "en-US",
    ): TvResponse?

    @GET("3/movie/{id}/recommendations?api_key=${API_KEY}")
    suspend fun getRecommendationMovie(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
    ): PageResponse<MovieResult>

    @GET("3/tv/{id}/recommendations?api_key=${API_KEY}")
    suspend fun getRecommendationTv(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
    ): PageResponse<TvResult>

    @GET("3/discover/tv?api_key=$API_KEY")
    suspend fun getDiscoverTv(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): PageResponse<TvResult>

    @GET("3/search/multi?api_key=$API_KEY")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PageResponse<MultiResult>
}