package com.the.movie.db.source.local.dao

import androidx.room.Dao
import com.the.movie.db.source.local.dao.discover.movie.DiscoverMovie
import com.the.movie.db.source.local.dao.movie.Movie
import com.the.movie.db.source.local.dao.movie.RecommendationMovie
import com.the.movie.db.source.local.database.TheMovieDbDatabase

@Dao
abstract class MovieDao(val db: TheMovieDbDatabase) : DiscoverMovie, Movie, RecommendationMovie