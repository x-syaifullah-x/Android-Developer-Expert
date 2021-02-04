package com.the.movie.db.source.local.entity.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the.movie.db.source.local.base.IResultEntity

@Entity(
    tableName = TvEntity.TABLE_NAME,
)
data class TvEntity(
    @PrimaryKey
    @ColumnInfo(name = PRIMARY_KEY)
    override val primaryKey: Int,
    override val backdropPath: String?,
    override val overview: String,
    override val posterPath: String?,
    override val voteAverage: Float,
    override val originalLanguage: String,

    val firstAirDate: String,
    val lastAirDate: String,
    val name: String,
    val originalName: String,
    @ColumnInfo(name = IS_FAVORITE)
    val isFavorite: Boolean = false,
    override val id: Int
) : IResultEntity {
    companion object {
        const val TABLE_NAME = "tv"
        const val PRIMARY_KEY = "primary_key"
        const val IS_FAVORITE = "is_favorite"
    }
}