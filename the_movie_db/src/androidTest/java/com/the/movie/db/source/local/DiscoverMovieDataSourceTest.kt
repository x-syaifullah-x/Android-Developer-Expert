package com.the.movie.db.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.the.movie.db.source.local.database.TheMovieDbDatabase
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import com.the.movie.db.utils.EspressoIdlingResource
import com.the.movie.db.utils.MainCoroutineScopeRulee
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

class DiscoverMovieDataSourceInstrumentTest {

    private lateinit var dataSource: DiscoverMovieDataSource

    private lateinit var db: TheMovieDbDatabase

    @Test
    fun insertResultEmptyTest() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TheMovieDbDatabase::class.java).build()

        dataSource = DiscoverMovieDataSource(db.movieDao())
        val results = dataSource.insert(fakePageResponse(listOf()))
        Assert.assertTrue(results)
    }

    @Test
    fun insertResultNotEmptyTest() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TheMovieDbDatabase::class.java).build()

        dataSource = DiscoverMovieDataSource(db.movieDao())
        val results = dataSource.insert(fakePageResponse(listOf(fakeMovieResult())))
        Assert.assertTrue(results)
    }

    private fun fakeMovieResult() = MovieResult("test", "test", "test", 90, "test", 0f, "test", "test")

    private fun fakePageResponse(result: List<MovieResult>) = PageResponse(
        1, 1, 1, result
    )
}