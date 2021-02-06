package com.the.movie.db.source.local

import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
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
    fun getPreviousPageIsNullTest(): Unit = runBlocking {
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovie(1))
        val results = dataSource.getPreviousPage()
        Assert.assertTrue(results == null)
    }

    @Test
    fun getPreviousPageIsReadyTest(): Unit = runBlocking {
        val totalPage = 10
        Mockito.`when`(dao.getLastPage()).thenReturn(getFakeDiscoverMovie(totalPage))
        val results = dataSource.getPreviousPage()
        Assert.assertTrue(results != null)
        Assert.assertTrue((results == totalPage - 1))
    }

    private fun getFakeDiscoverMovie(totalPage: Int) = DiscoverMovieEntity(
        totalPages = 1,
        totalResults = 1,
        page = totalPage
    )
}