package com.the.movie.db.source.local.dao.discover.movie

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity.Companion.ID
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity.Companion.TABLE_NAME

interface DiscoverMovieResult {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY ${DiscoverMovieResultEntity.PRIMARY_KEY} ASC")
    fun getResult(): PagingSource<Int, DiscoverMovieResultEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearDiscoverMovieResult(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDiscoverResults(list: List<DiscoverMovieResultEntity>): List<Long>

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME WHERE $ID = :id)")
    suspend fun isExist(id: Int): Boolean
}