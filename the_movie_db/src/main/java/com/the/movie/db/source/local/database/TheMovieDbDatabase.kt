package com.the.movie.db.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.dao.SearchDao
import com.the.movie.db.source.local.dao.TvDao
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity
import com.the.movie.db.source.local.entity.movie.MovieEntity
import com.the.movie.db.source.local.entity.movie.MovieGenreEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieEntity
import com.the.movie.db.source.local.entity.recommendation.movie.RecommendationMovieResultEntity
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvEntity
import com.the.movie.db.source.local.entity.recommendation.tv.RecommendationTvResultEntity
import com.the.movie.db.source.local.entity.search.SearchEntity
import com.the.movie.db.source.local.entity.tv.TvEntity
import com.the.movie.db.source.local.entity.tv.TvGenreEntity
import javax.inject.Singleton

@Database(
    entities = [
        DiscoverMovieEntity::class,
        DiscoverMovieResultEntity::class,
        DiscoverTvEntity::class,
        DiscoverTvResultEntity::class,
        MovieGenreEntity::class,
        TvGenreEntity::class,
        MovieEntity::class,
        TvEntity::class,
        RecommendationMovieEntity::class,
        RecommendationTvEntity::class,
        RecommendationMovieResultEntity::class,
        RecommendationTvResultEntity::class,
        SearchEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class TheMovieDbDatabase : RoomDatabase() {

    @Singleton
    abstract fun movieDao(): MovieDao

    @Singleton
    abstract fun tvDao(): TvDao

    @Singleton
    abstract fun searchDao(): SearchDao
}