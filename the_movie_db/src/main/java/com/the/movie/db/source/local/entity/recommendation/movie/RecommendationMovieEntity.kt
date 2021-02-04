package com.the.movie.db.source.local.entity.recommendation.movie

import androidx.room.*
import com.the.movie.db.source.local.base.IPage
import com.the.movie.db.source.local.entity.movie.MovieEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.ID
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [ID, PAGE], unique = true)],
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = [MovieEntity.PRIMARY_KEY],
        childColumns = [ID],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
class RecommendationMovieEntity(
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
        const val TABLE_NAME = "movie_recommendation"
        const val ID = "id"
        const val PAGE = "page"
    }
}