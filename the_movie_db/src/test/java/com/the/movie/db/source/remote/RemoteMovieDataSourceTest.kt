package com.the.movie.db.source.remote

import com.the.movie.db.data.fake.remote.FakeDataMovie
import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import test.utils.rule.RuleUnitTestWithCoroutine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteMovieDataSourceTest : RuleUnitTestWithCoroutine() {

    private lateinit var dataSource: RemoteMovieDataSource

    @Mock
    lateinit var apiService: MovieApiService

    override fun before() {
        dataSource = RemoteMovieDataSource(apiService)
    }

    @Test
    fun getDiscoverResultSuccess() = runBlocking {
        Mockito.`when`(apiService.getDiscoverMovie(page = 1))
            .thenReturn(FakeDataMovie.pageResponse)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun getDiscoverResultEmpty() = runBlocking {
        Mockito.`when`(apiService.getDiscoverMovie(page = 1))
            .thenReturn(FakeDataMovie.pageResponseResultsEmpty)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Empty)
    }

    @Test
    fun getDiscoverResultError() = runBlocking {
        Mockito.`when`(apiService.getDiscoverMovie(page = 1)).thenThrow(FakeDataMovie.exception)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun getMovieWithMovieRecommendationResultSuccess() = runBlocking {

        Mockito.`when`(apiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenReturn(FakeDataMovie.movieResponse)

        Mockito.`when`(apiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponse)

        dataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID)
            .collectLatest { response ->
                Assert.assertTrue(response is ApiResponse.Success)
                if (response is ApiResponse.Success) {
                    val dataFake = FakeDataMovie.movieWithRecommendation
                    val data = response.data
                    Assert.assertEquals(dataFake, data)
                    Assert.assertEquals(dataFake.recommendations, data.recommendations)
                }
            }
    }

    @Test
    fun getMovieWithMovieRecommendationResultEmpty(): Unit = runBlocking {

        Mockito.`when`(apiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenReturn(null)

        Mockito.`when`(apiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponseResultsEmpty)

        dataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID).apply {
            collectLatest {
                Assert.assertEquals(ApiResponse.Empty, it)
            }
        }
    }

    @Test
    fun getMovieWithMovieRecommendationResultError(): Unit = runBlocking {

        Mockito.`when`(apiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenThrow(FakeDataMovie.exception)

        Mockito.`when`(apiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponse)

        val results = dataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Error(FakeDataMovie.exception), it)
        }
    }
}