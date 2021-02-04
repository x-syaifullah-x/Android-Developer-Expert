package com.the.movie.db.source.local.entity.tv

import androidx.room.Embedded
import androidx.room.Relation
import com.the.movie.db.source.local.base.IDetail
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTv
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity

class Tv(
    @Embedded
    private val tvEntity: TvEntity,

    @Relation(
        parentColumn = TvEntity.PRIMARY_KEY,
        entityColumn = TvGenreEntity.FOREIGN_KEY,
        entity = TvGenreEntity::class
    )
    private val genres: List<TvGenreEntity>?,

    @Relation(
        parentColumn = TvEntity.PRIMARY_KEY,
        entityColumn = RecommendationTvEntity.ID,
        entity = RecommendationTvEntity::class,
    )
    private val recommendations: RecommendationTv?,
) : IDetail {
    override fun id(): Int = tvEntity.primaryKey
    override fun backdropPath(): String? = tvEntity.backdropPath
    override fun originalLanguage(): String = tvEntity.originalLanguage
    override fun overview(): String = tvEntity.overview
    override fun posterPath(): String? = tvEntity.posterPath
    override fun voteAverage(): Float = tvEntity.voteAverage

    fun lastAirDate(): String = tvEntity.lastAirDate
    fun firstAirDate(): String = tvEntity.firstAirDate
    fun originalName(): String = tvEntity.originalName
    fun genres(): List<TvGenreEntity>? = genres
    fun recommendations() = recommendations
    fun isFavorite() = tvEntity.isFavorite
}