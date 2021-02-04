package com.the.movie.db.source.local.dao.tv

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity.Companion.TABLE_NAME
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTv
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity
import kotlinx.coroutines.flow.Flow

interface RecommendationTv : RecommendationTvResult {
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE=:page")
    fun getRecommendation(page: Int): Flow<RecommendationTv?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recommendationMovieEntity: RecommendationTvEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<RecommendationTvEntity>): List<Long>
}