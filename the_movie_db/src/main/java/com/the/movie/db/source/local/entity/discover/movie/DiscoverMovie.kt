package com.the.movie.db.source.local.entity.discover.movie

import androidx.room.Embedded
import androidx.room.Relation
import com.the.movie.db.source.local.base.IPageEmbed

class DiscoverMovie(
    @Embedded
    private val discover: DiscoverMovieEntity,

    @Relation(
        parentColumn = DiscoverMovieEntity.PAGE,
        entityColumn = DiscoverMovieResultEntity.FOREIGN_KEY,
        entity = DiscoverMovieResultEntity::class
    )
    private val results: List<DiscoverMovieResultEntity>
) : IPageEmbed<DiscoverMovieResultEntity> {
    override fun page() = discover.page
    override fun totalPages() = discover.totalPages
    override fun totalResult() = discover.totalResults
    override fun results() = results
}