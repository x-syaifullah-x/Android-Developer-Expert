package com.the.movie.db.source.local.dao.tv

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.the.movie.db.source.local.entity.tv.TvGenreEntity

interface TvGenre {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(list: List<TvGenreEntity>): List<Long>
}