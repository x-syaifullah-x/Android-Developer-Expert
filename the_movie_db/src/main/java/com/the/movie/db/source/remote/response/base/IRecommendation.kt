package com.the.movie.db.source.remote.response.base

import com.the.movie.db.source.remote.response.PageResponse

interface IRecommendation<Result : IResult> {
    val recommendations: List<PageResponse<Result>>
}