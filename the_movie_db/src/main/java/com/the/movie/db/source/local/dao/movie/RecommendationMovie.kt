package com.the.movie.db.source.local.dao.movie

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovie
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

interface RecommendationMovie : RecommendationMovieResult {
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE=:page")
    fun getRecommendation(page: Int): Flow<RecommendationMovie?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recommendationMovieEntity: RecommendationMovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<RecommendationMovieEntity>): List<Long>
}