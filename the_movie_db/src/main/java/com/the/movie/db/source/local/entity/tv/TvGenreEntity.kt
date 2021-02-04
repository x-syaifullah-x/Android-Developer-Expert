package com.the.movie.db.source.local.entity.tv

import androidx.room.*
import com.the.movie.db.source.local.base.IEntity

@Entity(
    tableName = TvGenreEntity.TABLE_NAME,
    indices = [Index(
        value = [TvGenreEntity.FOREIGN_KEY, TvGenreEntity.ID, TvGenreEntity.NAME],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = TvEntity::class,
        parentColumns = [TvEntity.PRIMARY_KEY],
        childColumns = [TvGenreEntity.FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class TvGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PRIMARY_KEY)
    override val primaryKey: Long? = null,

    @ColumnInfo(name = ID)
    val id: Int,

    @ColumnInfo(name = FOREIGN_KEY)
    val foreignKey: Long,

    @ColumnInfo(name = NAME)
    val name: String

) : IEntity<Long> {
    companion object {
        const val TABLE_NAME = "tv_genre"
        const val ID = "id"
        const val NAME = "name"
        const val PRIMARY_KEY = "primary_key"
        const val FOREIGN_KEY = "foreign_key"
    }
}