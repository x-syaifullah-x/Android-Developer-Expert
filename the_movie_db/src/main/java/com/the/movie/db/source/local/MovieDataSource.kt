package com.the.movie.db.source.local

import com.the.movie.db.source.local.base.IEntity
import com.the.movie.db.source.local.base.IPageEmbed
import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.entity.movie.MovieEntity
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieResultEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val dao: MovieDao) {

    fun getMovieResult() = dao.getResult()

    fun getMovie(id: Int) = dao.getMovie(id)

    suspend fun setFavorite(id: Int, isFavorite: Boolean) = dao.update(id, isFavorite)

    fun getFavorite() = dao.getFavorite()

    suspend fun insert(
        movie: MovieEntity,
        genres: List<MovieGenreEntity>,
        recommendations: List<IPageEmbed<RecommendationMovieResultEntity>>
    ) = coroutineScope {
        val isFavorite = dao.isFavorite(movie.primaryKey) ?: false
        val foreignKey = dao.insert(movie.copy(isFavorite = isFavorite))

        if (IEntity.successInsert(foreignKey)) {
            launch { dao.insertMovieGenres(genres.map { it.copy(foreignKey = foreignKey) }) }
            launch { insertRecommendation(foreignKey, recommendations) }
        }
    }

    private suspend fun insertRecommendation(
        foreignKey: Long, pageEmbedded: List<IPageEmbed<RecommendationMovieResultEntity>>
    ) = coroutineScope {
        pageEmbedded.forEach {
            launch {
                val recommendationTvEntity = RecommendationMovieEntity(
                    id = foreignKey,
                    page = it.page(),
                    totalResults = it.totalResult(),
                    totalPages = it.totalPages()
                )
                dao.insert(recommendationTvEntity)
            }
            launch {
                val results = it.results().map { it.copy(foreignKey = foreignKey.toInt()) }
                dao.insertRecommendations(results)
            }
        }
    }
}