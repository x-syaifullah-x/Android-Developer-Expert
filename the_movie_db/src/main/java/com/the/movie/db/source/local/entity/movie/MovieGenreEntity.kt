package com.the.movie.db.source.local.entity.movie

import androidx.room.*
import com.the.movie.db.source.local.base.IEntity
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity.Companion.FOREIGN_KEY
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity.Companion.ID
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity.Companion.NAME
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [FOREIGN_KEY, ID, NAME], unique = true)],
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = [MovieEntity.PRIMARY_KEY],
        childColumns = [FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class MovieGenreEntity(
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
        const val TABLE_NAME = "movie_genre"
        const val ID = "id"
        const val NAME = "name"
        const val PRIMARY_KEY = "primary_key"
        const val FOREIGN_KEY = "foreign_key"
    }
}