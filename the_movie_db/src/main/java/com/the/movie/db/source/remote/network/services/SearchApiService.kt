package com.the.movie.db.source.remote.network.services

import com.the.movie.db.BuildConfig
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MultiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("3/search/multi?api_key=${BuildConfig.API_KEY}")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PageResponse<MultiResult>
}