package com.the.movie.db.source.local.dao.tv

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

interface RecommendationTvResult {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getRecommendation(): Flow<List<RecommendationTvResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(list: List<RecommendationTvResultEntity>): List<Long>
}