package com.the.movie.db.source.local.dao.discover.tv

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTv
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvEntity.Companion.PAGE
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

interface DiscoverTv : DiscoverTvResult {
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE=:page")
    fun getDiscover(page: Int): Flow<DiscoverTv?>

    @Query("SELECT * FROM $TABLE_NAME WHERE $PAGE = (SELECT MAX($PAGE) FROM $TABLE_NAME)")
    suspend fun getLastPage(): DiscoverTvEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(discoverTvEntity: DiscoverTvEntity): Long

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearDiscoverMovie(): Int
}