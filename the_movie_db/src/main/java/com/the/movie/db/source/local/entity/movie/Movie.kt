package com.the.movie.db.source.local.entity.movie

import androidx.room.Embedded
import androidx.room.Relation
import com.the.movie.db.source.local.base.IDetail
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovie
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity

class Movie(
    @Embedded
    private val movieEntity: MovieEntity,

    @Relation(
        parentColumn = MovieEntity.PRIMARY_KEY,
        entityColumn = MovieGenreEntity.FOREIGN_KEY,
        entity = MovieGenreEntity::class
    )
    private val genres: List<MovieGenreEntity>?,

    @Relation(
        parentColumn = MovieEntity.PRIMARY_KEY,
        entityColumn = RecommendationMovieEntity.ID,
        entity = RecommendationMovieEntity::class,
    )
    private val recommendations: RecommendationMovie?
) : IDetail {
    override fun id(): Int = movieEntity.primaryKey
    override fun backdropPath(): String? = movieEntity.backdropPath
    override fun originalLanguage(): String = movieEntity.originalLanguage
    override fun overview(): String = movieEntity.overview
    override fun posterPath(): String? = movieEntity.posterPath
    override fun voteAverage(): Float = movieEntity.voteAverage

    fun genres(): List<MovieGenreEntity>? = genres
    fun originalTitle(): String? = movieEntity.originalTitle
    fun releaseDate(): String? = movieEntity.releaseDate
    fun title(): String? = movieEntity.title
    fun recommendations() = recommendations
    fun isFavorite() = movieEntity.isFavorite
}