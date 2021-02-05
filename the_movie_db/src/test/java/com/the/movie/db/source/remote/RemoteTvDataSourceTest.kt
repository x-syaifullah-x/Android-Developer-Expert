package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.data.fake.FakeDataMovie
import com.the.movie.db.source.remote.data.fake.FakeDataTv
import com.the.movie.db.source.remote.network.services.TvApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RemoteTvDataSourceTest : RuleUnitTestWithMockito() {

    private lateinit var dataSource: RemoteTvDataSource

    @Mock
    lateinit var apiService: TvApiService

    override fun before() {
        dataSource = RemoteTvDataSource(apiService)
    }

    @Test
    fun getDiscoverResultSuccess() = runBlocking {
        Mockito.`when`(apiService.getDiscoverTv(page = 1))
            .thenReturn(FakeDataTv.pageResponse)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun getDiscoverResultEmpty() = runBlocking {
        Mockito.`when`(apiService.getDiscoverTv(page = 1))
            .thenReturn(FakeDataTv.pageResponseResultsEmpty)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Empty)
    }

    @Test
    fun getDiscoverResultError() = runBlocking {
        Mockito.`when`(apiService.getDiscoverTv(page = 1)).thenThrow(FakeDataMovie.exception)
        val result = dataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun getMovieWithMovieRecommendationResultSuccess() = runBlocking {

        Mockito.`when`(apiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenReturn(FakeDataTv.tvResponse)

        Mockito.`when`(apiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponse)

        dataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID)
            .collectLatest { response ->
                Assert.assertTrue(response is ApiResponse.Success)
                if (response is ApiResponse.Success) {
                    val dataFake = FakeDataTv.tvWithRecommendation
                    val data = response.data
                    Assert.assertEquals(dataFake, data)
                    Assert.assertEquals(dataFake.recommendations, data.recommendations)
                }
            }
    }

    @Test
    fun getMovieWithMovieRecommendationResultEmpty(): Unit = runBlocking {
        Mockito.`when`(apiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenReturn(null)

        Mockito.`when`(apiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponseResultsEmpty)

        dataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID).apply {
            collectLatest {
                Assert.assertEquals(ApiResponse.Empty, it)
            }
        }
    }

    @Test
    fun getMovieWithMovieRecommendationResultError(): Unit = runBlocking {
        Mockito.`when`(apiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenThrow(FakeDataTv.exception)

        Mockito.`when`(apiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponse)

        val results = dataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Error(FakeDataTv.exception), it)
        }
    }
}