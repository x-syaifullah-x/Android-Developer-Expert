package com.the.movie.db.source.local.entity.discover.tv

import androidx.room.*
import com.the.movie.db.source.local.base.ITvResultEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity.Companion.FOREIGN_KEY
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity.Companion.ID
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [ID, FOREIGN_KEY], unique = true)],
    foreignKeys = [ForeignKey(
        entity = DiscoverTvEntity::class,
        parentColumns = [DiscoverTvEntity.PAGE],
        childColumns = [FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class DiscoverTvResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PRIMARY_KEY)
    override val primaryKey: Int? = null,
    override val posterPath: String?,
    override val backdropPath: String?,
    override val voteAverage: Float,
    override val overview: String,
    @ColumnInfo(name = ID)
    override val id: Int,

    @ColumnInfo(name = FOREIGN_KEY)
    override val foreignKey: Int,
    override val originalName: String,
    override val name: String,
    override val originalLanguage: String,
) : ITvResultEntity {
    companion object {
        const val ID = "id"
        const val FOREIGN_KEY = "foreign_key"
        const val PRIMARY_KEY = "primary_key"
        const val TABLE_NAME = "discover_tv_result"
    }
}