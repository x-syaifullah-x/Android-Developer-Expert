package com.android.developer.expert.core.data.mapper

import com.android.developer.expert.core.domain.model.*
import com.android.developer.expert.core.domain.model.base.IModel
import com.the.movie.db.source.local.base.IResultEntity
import com.the.movie.db.source.local.entity.movie.Movie
import com.the.movie.db.source.local.entity.search.SearchEntity
import com.the.movie.db.source.local.entity.tv.Tv

private fun imgURL(path: String?) = "https://image.tmdb.org/t/p/original/$path"

fun Movie?.toDetailMovieModel() = DetailMovieModel(
    id = this?.id() ?: IModel.DATA_NOT_VALID,
    backdropPath = imgURL(this?.backdropPath() ?: this?.posterPath()),
    genres = this?.genres()?.map { GenreModel(id = it.id, it.name) } ?: listOf(),
    originalLanguage = this?.originalLanguage() ?: "-",
    overview = this?.overview() ?: "-",
    urlPosterPath = imgURL(this?.posterPath() ?: this?.backdropPath()),
    title = this?.title() ?: "-",
    releaseDate = this?.releaseDate() ?: "-",
    voteAverage = this?.voteAverage() ?: 0f,
    recommendation = this?.recommendations()?.results()
        ?.map { it.toItemModel(it.title) } ?: listOf(),
    isFavorite = this?.isFavorite() == true
)

fun Tv?.toDetailTvModel() = DetailTvModel(
    id = this?.id() ?: IModel.DATA_NOT_VALID,
    backdropPath = imgURL(this?.backdropPath() ?: this?.posterPath()),
    genres = this?.genres()?.map { GenreModel(id = it.id, it.name) } ?: listOf(),
    originalLanguage = this?.originalLanguage() ?: "-",
    overview = this?.overview() ?: "-",
    urlPosterPath = imgURL(this?.posterPath() ?: this?.backdropPath()),
    title = this?.originalName() ?: "-",
    voteAverage = this?.voteAverage() ?: 0f,
    recommendation = this?.recommendations()?.results()
        ?.map { it.toItemModel(it.name) } ?: listOf(),
    isFavorite = this?.isFavorite() == true,
    firstAirDate = this?.firstAirDate() ?: "-",
    lastAirDate = this?.lastAirDate() ?: "-"
)

fun SearchEntity?.toSearchModel() = SearchModel(
    id = this?.primaryKey,
    title = this?.title,
    mediaType = this?.mediaType
)

fun <T : IResultEntity> T.toItemModel(name: String?) = ItemModel(
    id = id,
    name = name ?: "-",
    overview = overview ?: "-",
    urlPosterPath = imgURL(posterPath),
    voteAverage = voteAverage,
)