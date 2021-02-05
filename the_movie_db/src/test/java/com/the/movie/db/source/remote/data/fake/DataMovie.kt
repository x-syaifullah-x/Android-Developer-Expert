package com.the.movie.db.source.remote.data.fake

import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import com.the.movie.db.source.remote.response.model.MovieWithRecommendation

object DataMovie {
    const val FAKE_ID = 22323

    val movieResponse = MovieResponse(
        backdropPath = "test",
        genres = listOf(),
        id = 1,
        originalLanguage = "test",
        originalTitle = "test",
        overview = "test",
        posterPath = "test",
        releaseDate = "test",
        title = "test",
        voteAverage = 0F
    )

    val dataRecommendation = MovieResult(
        originalTitle = "test",
        title = "test",
        posterPath = "test",
        id = 102,
        backdropPath = "test",
        voteAverage = 0F,
        overview = "test",
        originalLanguage = "test"
    )

    val pageResponse = PageResponse(
        page = 1,
        results = listOf(dataRecommendation),
        totalPages = 1,
        totalResults = 1
    )

    val movieWithRecommendation = MovieWithRecommendation(
        backdropPath = movieResponse.backdropPath,
        genres = movieResponse.genres,
        id = movieResponse.id,
        originalLanguage = movieResponse.originalLanguage,
        originalTitle = movieResponse.originalTitle,
        overview = movieResponse.overview,
        posterPath = movieResponse.posterPath,
        releaseDate = movieResponse.releaseDate,
        title = movieResponse.title,
        voteAverage = movieResponse.voteAverage,
        recommendations = listOf(pageResponse)
    )
}