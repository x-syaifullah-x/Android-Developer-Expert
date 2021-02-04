package com.the.movie.db.source.local.entity.discover.tv

import androidx.room.Embedded
import androidx.room.Relation
import com.the.movie.db.source.local.base.IPageEmbed

class DiscoverTv(
    @Embedded
    private val discover: DiscoverTvEntity,

    @Relation(
        parentColumn = DiscoverTvEntity.PAGE,
        entityColumn = DiscoverTvResultEntity.FOREIGN_KEY,
        entity = DiscoverTvResultEntity::class
    )
    private val results: List<DiscoverTvResultEntity>
) : IPageEmbed<DiscoverTvResultEntity> {
    override fun page() = discover.page
    override fun totalPages() = discover.totalPages
    override fun totalResult() = discover.totalResults
    override fun results() = results
}