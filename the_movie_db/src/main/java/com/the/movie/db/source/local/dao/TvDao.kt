package com.the.movie.db.source.local.dao

import androidx.room.Dao
import com.the.movie.db.source.local.dao.discover.tv.DiscoverTv
import com.the.movie.db.source.local.dao.tv.RecommendationTv
import com.the.movie.db.source.local.dao.tv.Tv
import com.the.movie.db.source.local.database.TheMovieDbDatabase

@Dao
abstract class TvDao(val db: TheMovieDbDatabase) : DiscoverTv, Tv, RecommendationTv