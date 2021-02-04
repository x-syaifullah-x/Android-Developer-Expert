package com.the.movie.db.source.local.dao.discover.movie

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovie
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

interface DiscoverMovie : DiscoverMovieResult {
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE=:page")
    fun getDiscover(page: Int): Flow<DiscoverMovie?>

    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE = (SELECT MAX($PAGE) FROM $TABLE_NAME)")
    suspend fun getLastPage(): DiscoverMovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(discoverMovieEntity: DiscoverMovieEntity): Long

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearDiscoverMovie(): Int
}