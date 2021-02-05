package com.the.movie.db.mapper

import com.the.movie.db.source.local.base.IPageEmbed
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity
import com.the.movie.db.source.local.entity.movie.MovieEntity
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieResultEntity
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity
import com.the.movie.db.source.local.entity.search.SearchEntity
import com.the.movie.db.source.local.entity.tv.TvEntity
import com.the.movie.db.source.local.entity.tv.TvGenreEntity
import com.the.movie.db.source.remote.response.MovieResponse
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.TvResponse
import com.the.movie.db.source.remote.response.model.*

fun MultiResult.toSearchEntity() = SearchEntity(
    primaryKey = id,
    title = title ?: originalTitle ?: originalName ?: name ?: "-",
    mediaType = mediaType
)

fun MovieResult.toDiscoverMovieResultEntity(fk: Int) = DiscoverMovieResultEntity(
    foreignKey = fk,
    posterPath = posterPath ?: backdropPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    overview = overview,
    id = id,
    originalTitle = originalTitle,
    title = title,
    originalLanguage = originalLanguage
)

fun TvResult.toDiscoverTvResultEntity(fk: Int) = DiscoverTvResultEntity(
    foreignKey = fk,
    posterPath = posterPath,
    backdropPath = backdropPath ?: posterPath,
    voteAverage = voteAverage,
    overview = overview,
    id = id,
    name = name,
    originalName = originalName,
    originalLanguage = originalLanguage
)

fun MovieResult.toRecommendationMovieResultEntity(fk: Int) = RecommendationMovieResultEntity(
    primaryKey = id,
    foreignKey = fk,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    overview = overview,
    id = id,
    originalTitle = originalTitle,
    title = title,
    originalLanguage = originalLanguage
)

fun TvResult.toRecommendationMovieResultEntity(fk: Int) = RecommendationTvResultEntity(
    primaryKey = id,
    foreignKey = fk,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    overview = overview,
    id = id,
    name = name,
    originalName = originalName,
    originalLanguage = originalLanguage
)

fun Genres.toMovieGenreEntity() = MovieGenreEntity(id = id, name = name, foreignKey = 0)

fun Genres.toTvGenreEntity() = TvGenreEntity(id = id, name = name, foreignKey = 0)

fun TvWithTvRecommendation.toTvEntity() = TvEntity(
    primaryKey = id,
    backdropPath = backdropPath,
    overview = overview,
    posterPath = posterPath,
    voteAverage = voteAverage,
    firstAirDate = firstAirDate,
    lastAirDate = lastAirDate,
    name = name,
    originalName = originalName,
    id = id,
    originalLanguage = originalLanguage
)

fun MovieWithRecommendation.toMovieEntity() = MovieEntity(
    primaryKey = id,
    backdropPath = backdropPath,
    title = title,
    releaseDate = releaseDate,
    overview = overview,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    posterPath = posterPath,
    id = id,
    originalLanguage = originalLanguage
)

fun MovieResponse.toMovieWithRecommendation(movieResults: List<PageResponse<MovieResult>>) =
    MovieWithRecommendation(
        id = id,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        title = title,
        releaseDate = releaseDate,
        overview = overview,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        posterPath = posterPath,
        genres = genres,
        recommendations = movieResults,
    )

fun TvResponse.toTvWithTvRecommendation(lis: List<PageResponse<TvResult>>) = TvWithTvRecommendation(
    backdropPath = backdropPath,
    genres = genres,
    id = id,
    originalLanguage = originalLanguage,
    overview = overview,
    posterPath = posterPath,
    voteAverage = voteAverage,
    firstAirDate = firstAirDate,
    lastAirDate = lastAirDate,
    name = name,
    originalName = originalName,
    recommendations = lis
)

fun PageResponse<MovieResult>.toPageEmbedded() =
    object : IPageEmbed<RecommendationMovieResultEntity> {
        override fun page() = page
        override fun totalPages() = totalPages
        override fun totalResult() = totalResults
        override fun results() = results.map { it.toRecommendationMovieResultEntity(0) }
    }

@JvmName("toPageEmbeddedTvResult")
fun PageResponse<TvResult>.toPageEmbedded() = object : IPageEmbed<RecommendationTvResultEntity> {
    override fun page() = page
    override fun totalPages() = totalPages
    override fun totalResult() = totalResults
    override fun results() = results.map { it.toRecommendationMovieResultEntity(0) }
}