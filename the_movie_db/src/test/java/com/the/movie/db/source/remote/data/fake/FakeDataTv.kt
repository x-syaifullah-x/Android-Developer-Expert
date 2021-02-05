package com.the.movie.db.source.remote.data.fake

import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.TvResponse
import com.the.movie.db.source.remote.response.model.TvResult
import com.the.movie.db.source.remote.response.model.TvWithTvRecommendation
import org.mockito.exceptions.base.MockitoException

object FakeDataTv {

    const val FAKE_ID = 22323

    val exception = MockitoException("test error")

    val tvResponse = TvResponse(
        backdropPath = "test",
        genres = listOf(),
        id = 1,
        originalLanguage = "test",
        overview = "test",
        posterPath = "test",
        voteAverage = 0F,
        firstAirDate = "test",
        name = "test",
        lastAirDate = "test",
        originalName = "test"
    )

    private val tvResult = TvResult(
        posterPath = "test",
        id = 102,
        backdropPath = "test",
        voteAverage = 0F,
        overview = "test",
        originalLanguage = "test",
        originalName = "test",
        name = "test"
    )

    val pageResponse = PageResponse(
        page = 1,
        results = listOf(tvResult),
        totalPages = 1,
        totalResults = 1
    )

    val pageResponseResultsEmpty = PageResponse(
        page = 0,
        results = listOf<TvResult>(),
        totalPages = 0,
        totalResults = 0
    )

    val tvWithRecommendation = TvWithTvRecommendation(
        backdropPath = tvResponse.backdropPath,
        genres = tvResponse.genres,
        id = tvResponse.id,
        originalLanguage = tvResponse.originalLanguage,
        overview = tvResponse.overview,
        posterPath = tvResponse.posterPath,
        voteAverage = tvResponse.voteAverage,
        recommendations = listOf(pageResponse),
        name = tvResponse.name,
        originalName = tvResponse.originalName,
        lastAirDate = tvResponse.lastAirDate,
        firstAirDate = tvResponse.firstAirDate
    )
}