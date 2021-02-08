package com.the.movie.db.source.local

import com.the.movie.db.data.fake.local.FakeData
import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.dao.TvDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import test.utils.rule.RuleUnitTestWithCoroutine

@RunWith(MockitoJUnitRunner::class)
class DiscoverTvDataSourceTest : RuleUnitTestWithCoroutine() {
    private lateinit var dataSource: DiscoverTvDataSource

    @Mock
    lateinit var dao: TvDao

    override fun before() {
        dataSource = DiscoverTvDataSource(dao)
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
        Mockito.`when`(dao.getLastPage()).thenReturn(FakeData.getDiscoverTvEntity(1))
        val results = dataSource.getPreviousPage()
        Assert.assertTrue(results == null)
    }

    @Test
    fun getPreviousPageIsReadyTest(): Unit = runBlocking {
        val totalPage = 10
        Mockito.`when`(dao.getLastPage()).thenReturn(FakeData.getDiscoverTvEntity(totalPage))
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
        Mockito.`when`(dao.getLastPage()).thenReturn(FakeData.getDiscoverTvEntity(totalPage))

        val results = dataSource.getCurrentPage()
        Assert.assertTrue(results != null)
        Assert.assertTrue(results?.totalPages == totalPage)
    }

    @Test
    fun getNextPageIsNotNullTest(): Unit = runBlocking {
        val totalPage = 2
        Mockito.`when`(dao.getLastPage()).thenReturn(FakeData.getDiscoverTvEntity(totalPage))
        val results = dataSource.getNextPage()
        Assert.assertTrue(results == totalPage + 1)
    }

    @Test
    fun getNextPageIsNullTest(): Unit = runBlocking {
        val totalPage = 1
        Mockito.`when`(dao.getLastPage()).thenReturn(FakeData.getDiscoverTvEntity(totalPage))
        val results = dataSource.getNextPage()
        Assert.assertTrue(results == null)
    }
}