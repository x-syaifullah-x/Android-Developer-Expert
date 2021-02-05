package com.the.movie.db.source.remote.data.fake

import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MultiResult
import org.mockito.exceptions.base.MockitoException

object FakeDataSearch {

    const val FAKE_QUERY = "TEST"

    val exception = MockitoException("test error")

    val pageResponseEmpty = PageResponse(
        page = 0,
        totalResults = 0,
        totalPages = 0,
        results = listOf<MultiResult>()
    )

    private val multiResult = MultiResult(
        originalName = "test",
        originalLanguage = "test",
        name = "test",
        title = "test",
        overview = "test",
        voteAverage = 0F,
        id = 102,
        posterPath = "test",
        originalTitle = "test",
        backdropPath = "test",
        mediaType = "test"
    )

    val pageResponse = PageResponse(
        page = 1,
        totalResults = 1,
        totalPages = 1,
        results = listOf(multiResult)
    )
}