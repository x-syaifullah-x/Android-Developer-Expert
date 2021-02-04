package com.the.movie.db.source.local.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the.movie.db.source.local.base.IResultEntity

@Entity(
    tableName = MovieEntity.TABLE_NAME,
)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = PRIMARY_KEY)
    override val primaryKey: Int,
    override val backdropPath: String?,
    override val overview: String,
    override val posterPath: String?,
    override val voteAverage: Float,
    override val originalLanguage: String,
    val originalTitle: String?,
    val releaseDate: String?,
    val title: String?,
    @ColumnInfo(name = IS_FAVORITE)
    val isFavorite: Boolean = false,
    override val id: Int
) : IResultEntity {
    companion object {
        const val TABLE_NAME = "movie"
        const val PRIMARY_KEY = "primary_key"
        const val IS_FAVORITE = "is_favorite"
    }
}