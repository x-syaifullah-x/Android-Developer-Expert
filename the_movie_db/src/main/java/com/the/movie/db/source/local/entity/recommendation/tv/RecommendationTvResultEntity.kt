package com.the.movie.db.source.local.entity.recommendation.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.the.movie.db.source.local.base.ITvResultEntity
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity.Companion.FOREIGN_KEY
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity.Companion.TABLE_NAME
import com.the.movie.db.source.local.entity.tv.TvEntity

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = TvEntity::class,
        parentColumns = [TvEntity.PRIMARY_KEY],
        childColumns = [FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class RecommendationTvResultEntity(
    @PrimaryKey
    @ColumnInfo(name = PRIMARY_KEY)
    override val primaryKey: Int,
    override val posterPath: String?,
    override val backdropPath: String?,
    override val voteAverage: Float,
    override val overview: String,

    @ColumnInfo(name = FOREIGN_KEY)
    override val foreignKey: Int,
    override val id: Int,
    override val originalName: String,
    override val name: String,
    override val originalLanguage: String,
) : ITvResultEntity {
    companion object {
        const val PRIMARY_KEY = "primary_key"
        const val TABLE_NAME = "tv_recommendation_result"
        const val FOREIGN_KEY = "foreign_key"
    }
}