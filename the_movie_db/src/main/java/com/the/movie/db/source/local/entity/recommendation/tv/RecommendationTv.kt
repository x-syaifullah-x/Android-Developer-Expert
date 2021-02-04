package com.the.movie.db.source.local.entity.recommendation.tv

import androidx.room.Embedded
import androidx.room.Relation
import com.the.movie.db.source.local.base.IPageEmbed

class RecommendationTv(
    @Embedded
    private val discover: RecommendationTvEntity,

    @Relation(
        parentColumn = RecommendationTvEntity.ID,
        entityColumn = RecommendationTvResultEntity.FOREIGN_KEY,
        entity = RecommendationTvResultEntity::class
    )
    private val results: List<RecommendationTvResultEntity>
) : IPageEmbed<RecommendationTvResultEntity> {
    override fun page() = discover.page
    override fun totalPages() = discover.totalPages
    override fun totalResult() = discover.totalResults
    override fun results() = results
}