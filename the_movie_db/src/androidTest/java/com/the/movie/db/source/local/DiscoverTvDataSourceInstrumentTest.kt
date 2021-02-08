package com.the.movie.db.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.the.movie.db.data.fake.remote.Data
import com.the.movie.db.source.local.database.TheMovieDbDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class DiscoverTvDataSourceInstrumentTest {

    private lateinit var dataSource: DiscoverTvDataSource

    private lateinit var db: TheMovieDbDatabase

    @Test
    fun insertResultEmptyTest() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TheMovieDbDatabase::class.java).build()

        dataSource = DiscoverTvDataSource(db.tvDao())
        val results = dataSource.insert(Data.fakePageResponse(listOf()))
        Assert.assertTrue(results)
    }

    @Test
    fun insertResultNotEmptyTest() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TheMovieDbDatabase::class.java).build()

        dataSource = DiscoverTvDataSource(db.tvDao())
        val results = dataSource.insert(Data.fakePageResponse(listOf(Data.fakeTvResult())))
        Assert.assertTrue(results)
    }
}