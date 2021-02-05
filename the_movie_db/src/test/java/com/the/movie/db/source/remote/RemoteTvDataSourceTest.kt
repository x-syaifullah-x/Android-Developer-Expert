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

    private lateinit var remoteTvDataSource: RemoteTvDataSource

    @Mock
    lateinit var tvApiService: TvApiService

    override fun before() {
        remoteTvDataSource = RemoteTvDataSource(tvApiService)
    }

    @Test
    fun getDiscoverResultSuccess() = runBlocking {
        Mockito.`when`(tvApiService.getDiscoverTv(page = 1))
            .thenReturn(FakeDataTv.pageResponse)
        val result = remoteTvDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun getDiscoverResultEmpty() = runBlocking {
        Mockito.`when`(tvApiService.getDiscoverTv(page = 1))
            .thenReturn(FakeDataTv.pageResponseResultsEmpty)
        val result = remoteTvDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Empty)
    }

    @Test
    fun getDiscoverResultError() = runBlocking {
        Mockito.`when`(tvApiService.getDiscoverTv(page = 1)).thenThrow(FakeDataMovie.exception)
        val result = remoteTvDataSource.getDiscover(1)
        Assert.assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun getMovieWithMovieRecommendationResultSuccess() = runBlocking {

        Mockito.`when`(tvApiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenReturn(FakeDataTv.tvResponse)

        Mockito.`when`(tvApiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponse)

        remoteTvDataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID)
            .collectLatest { response ->
                Assert.assertTrue(response is ApiResponse.Success)
                if (response is ApiResponse.Success) {
                    Assert.assertEquals(FakeDataTv.tvWithRecommendation, response.data)
                }
            }
    }

    @Test
    fun getMovieWithMovieRecommendationResultEmpty(): Unit = runBlocking {

        Mockito.`when`(tvApiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenReturn(null)

        Mockito.`when`(tvApiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponseResultsEmpty)

        remoteTvDataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID).apply {
            collectLatest {
                Assert.assertEquals(ApiResponse.Empty, it)
            }
        }
    }

    @Test
    fun getMovieWithMovieRecommendationResultError(): Unit = runBlocking {

        Mockito.`when`(tvApiService.getTv(id = FakeDataTv.FAKE_ID))
            .thenThrow(FakeDataTv.exception)

        Mockito.`when`(tvApiService.getRecommendationTv(id = FakeDataTv.FAKE_ID, 1))
            .thenReturn(FakeDataTv.pageResponse)

        val results = remoteTvDataSource.getTvWithTvRecommendation(FakeDataTv.FAKE_ID)

        results.collectLatest {
            Assert.assertEquals(ApiResponse.Error(FakeDataTv.exception), it)
        }
    }
}