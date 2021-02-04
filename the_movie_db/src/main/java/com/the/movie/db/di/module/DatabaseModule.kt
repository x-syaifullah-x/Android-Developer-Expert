package com.the.movie.db.di.module

import android.app.Application
import androidx.room.Room
import com.the.movie.db.source.local.database.TheMovieDbDatabase
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, TheMovieDbDatabase::class.java, this::class.java.name)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("passphrase".toCharArray())))
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(@Singleton database: TheMovieDbDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideTvDao(@Singleton database: TheMovieDbDatabase) = database.tvDao()

    @Singleton
    @Provides
    fun provideSearchDao(@Singleton database: TheMovieDbDatabase) = database.searchDao()
}