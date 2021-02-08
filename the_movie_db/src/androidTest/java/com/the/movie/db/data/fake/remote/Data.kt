package com.the.movie.db.data.fake.remote

import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.base.IResult
import com.the.movie.db.source.remote.response.model.MovieResult
import com.the.movie.db.source.remote.response.model.TvResult

object Data {

    fun fakeMovieResult() = MovieResult("test", "test", "test", 90, "test", 0f, "test", "test")

    fun fakeTvResult() = TvResult("test", "test", "test", 90, "test", 0f, "test", "test")


    fun <T : IResult> fakePageResponse(result: List<T>) = PageResponse(
        1, 1, 1, result
    )

}