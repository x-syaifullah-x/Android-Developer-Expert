package com.the.movie.db.source.local

import android.content.Context
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import com.the.movie.db.R
import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import com.the.movie.db.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class DiscoverMovieDataSourceTest : RuleUnitTestWithMockito() {
    private lateinit var dataSource: DiscoverMovieDataSource

    @Mock
    lateinit var dao: MovieDao

    override fun before() {
        dataSource = DiscoverMovieDataSource(dao)
    }

    @Test
    fun clearDiscoverSuccessTest(): Unit = runBlocking {
        val successClear = 1
        Mockito.`when`(dao.clearDiscoverMovie()).thenReturn(successClear)
        val results = dataSource.clearDiscover()
        Assert.assertTrue(results == successClear)
    }

    @Test
    fun clearDiscoverFailTest(): Unit = runBlocking {
        val successClear = 0
        Mockito.`when`(dao.clearDiscoverMovie()).thenReturn(successClear)
        val results = dataSource.clearDiscover()
        Assert.assertTrue(results == successClear)
    }

    @Test
    fun getPreviousPageIsNullTest(): Unit = runBlocking {
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovieEntity(1))
        val results = dataSource.getPreviousPage()
        Assert.assertTrue(results == null)
    }

    @Test
    fun getPreviousPageIsReadyTest(): Unit = runBlocking {
        val totalPage = 10
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovieEntity(totalPage))
        val results = dataSource.getPreviousPage()
        Assert.assertTrue(results != null)
        Assert.assertTrue((results == totalPage - 1))
    }

    @Test
    fun getCurrentPageIsNullTest(): Unit = runBlocking {
        Mockito.`when`(dao.getLastPage()).thenReturn(null)
        val results = dataSource.getCurrentPage()
        Assert.assertTrue(results == null)
    }

    @Test
    fun getCurrentPageIsNotNullTest(): Unit = runBlocking {
        val totalPage = 1
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovieEntity(totalPage))

        val results = dataSource.getCurrentPage()
        Assert.assertTrue(results != null)
        Assert.assertTrue(results?.totalPages == totalPage)
    }

    @Test
    fun getNextPageIsNotNullTest(): Unit = runBlocking {
        val totalPage = 2
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovieEntity(totalPage))
        val results = dataSource.getNextPage()
        Assert.assertTrue(results == totalPage + 1)
    }

    @Test
    fun getNextPageIsNullTest(): Unit = runBlocking {
        val totalPage = 1
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovieEntity(totalPage))
        val results = dataSource.getNextPage()
        Assert.assertTrue(results == null)
    }

    @Test
    fun insert(): Unit = runBlocking {
//        val fakeData = PageResponse<MovieResult>(1, 1, 1, listOf())
//
//        Mockito.`when`(dao.insert(getFakeDiscoverMovieEntity(1))).thenReturn(1)
//        Mockito.`when`(dao.insertDiscoverResults(listOf())).thenReturn(listOf())
//
//        dataSource.insert(fakeData)

        }

    private fun getFakeDiscoverMovieEntity(totalPage: Int) = DiscoverMovieEntity(
        totalPages = 1,
        totalResults = 1,
        page = totalPage
    )
}