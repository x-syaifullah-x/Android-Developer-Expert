package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.data.fake.DataMovie
import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

class RemoteMovieDataSourceTest : RuleUnitTestWithMockito() {

    private lateinit var remoteMovieDataSource: RemoteMovieDataSource

    @Mock
    lateinit var movieApiService: MovieApiService

    override fun before() {
        remoteMovieDataSource = RemoteMovieDataSource(movieApiService)
    }

    @Test
    fun getMovieWithMovieRecommendationResultSuccess() = runBlocking {

        Mockito.`when`(movieApiService.getMovie(id = DataMovie.FAKE_ID))
            .thenReturn(DataMovie.movieResponse)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = DataMovie.FAKE_ID, 1))
            .thenReturn(DataMovie.pageResponse)

        remoteMovieDataSource.getMovieWithMovieRecommendation(DataMovie.FAKE_ID)
            .collectLatest { response ->
                Assert.assertTrue(response is ApiResponse.Success)
                if (response is ApiResponse.Success) {
                    Assert.assertEquals(DataMovie.movieWithRecommendation, response.data)
                }
            }
    }

    @Test
    fun getMovieWithMovieRecommendationResultEmpty() = runBlocking {

        Mockito.`when`(movieApiService.getMovie(id = DataMovie.FAKE_ID))
            .thenReturn(null)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = DataMovie.FAKE_ID, 1))
            .thenReturn(DataMovie.pageResponse)

        val results = remoteMovieDataSource.getMovieWithMovieRecommendation(DataMovie.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Empty, it)
        }
    }

    @Test
    fun getMovieWithMovieRecommendationResultError(): Unit = runBlocking {

        val exception = MockitoException("test error")

        Mockito.`when`(movieApiService.getMovie(id = DataMovie.FAKE_ID)).thenThrow(exception)

        Mockito.`when`(movieApiService.getRecommendationMovie(id = DataMovie.FAKE_ID, 1))
            .thenReturn(DataMovie.pageResponse)

        val results = remoteMovieDataSource.getMovieWithMovieRecommendation(DataMovie.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Error(exception), it)
        }
    }
}