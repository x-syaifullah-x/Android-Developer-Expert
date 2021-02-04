package com.the.movie.db.source.local.entity.recommendation.tv

import androidx.room.*
import com.the.movie.db.source.local.base.IPage
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity.Companion.ID
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity.Companion.TABLE_NAME
import com.the.movie.db.source.local.entity.tv.TvEntity

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [ID, PAGE], unique = true)],
    foreignKeys = [ForeignKey(
        entity = TvEntity::class,
        parentColumns = [TvEntity.PRIMARY_KEY],
        childColumns = [ID],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
class RecommendationTvEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Long? = null,

    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = PAGE)
    override val page: Int,

    @ColumnInfo(name = "total_result")
    override val totalResults: Int,

    @ColumnInfo(name = "total_pages")
    override val totalPages: Int
) : IPage {
    companion object {
        const val TABLE_NAME = "tv_recommendation"
        const val ID = "id"
        const val PAGE = "page"
    }
}