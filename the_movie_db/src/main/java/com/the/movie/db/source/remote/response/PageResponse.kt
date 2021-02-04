package com.the.movie.db.source.remote.response

import com.google.gson.annotations.SerializedName
import com.the.movie.db.source.remote.response.base.IResult

data class PageResponse<Result : IResult>(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<Result>
)