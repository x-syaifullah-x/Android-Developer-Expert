package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.data.fake.FakeDataMovie
import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RemoteMovieDataSourceTest : RuleUnitTestWithMockito() {

    private lateinit var remoteMovieDataSource: RemoteMovieDataSource

    @Mock
    lateinit var movieApiService: MovieApiService

    override fun before() {
        remoteMovieDataSource = RemoteMovieDataSource(movieApiService)
    }

    @Test
    fun getDiscoverResultSuccess() = runBlocking {
        Mockito.`when`(movieApiService.getDiscoverMovie(page = 1))
            .thenReturn(FakeDataMovie.pageResponse)
        val result = remoteMovieDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun getDiscoverResultEmpty() = runBlocking {
        Mockito.`when`(movieApiService.getDiscoverMovie(page = 1))
            .thenReturn(FakeDataMovie.pageResponseResultsEmpty)
        val result = remoteMovieDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Empty)
    }

    @Test
    fun getDiscoverResultError() = runBlocking {
        Mockito.`when`(movieApiService.getDiscoverMovie(page = 1)).thenThrow(FakeDataMovie.exception)
        val result = remoteMovieDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun getMovieWithMovieRecommendationResultSuccess() = runBlocking {

        Mockito.`when`(movieApiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenReturn(FakeDataMovie.movieResponse)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponse)

        remoteMovieDataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID)
            .collectLatest { response ->
                Assert.assertTrue(response is ApiResponse.Success)
                if (response is ApiResponse.Success) {
                    Assert.assertEquals(FakeDataMovie.movieWithRecommendation, response.data)
                }
            }
    }

    @Test
    fun getMovieWithMovieRecommendationResultEmpty(): Unit = runBlocking {

        Mockito.`when`(movieApiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenReturn(null)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponseResultsEmpty)

        remoteMovieDataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID).apply {
            collectLatest {
                Assert.assertEquals(ApiResponse.Empty, it)
            }
        }
    }

    @Test
    fun getMovieWithMovieRecommendationResultError(): Unit = runBlocking {

        Mockito.`when`(movieApiService.getMovie(id = FakeDataMovie.FAKE_ID))
            .thenThrow(FakeDataMovie.exception)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = FakeDataMovie.FAKE_ID, 1))
            .thenReturn(FakeDataMovie.pageResponse)

        val results = remoteMovieDataSource.getMovieWithMovieRecommendation(FakeDataMovie.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Error(FakeDataMovie.exception), it)
        }
    }
}