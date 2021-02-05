package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.data.fake.FakeDataSearch
import com.the.movie.db.source.remote.network.services.SearchApiService
import com.the.movie.db.source.remote.network.utils.ApiResponse
import com.the.movie.db.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RemoteSearchDataSourceTest : RuleUnitTestWithMockito() {
    private lateinit var dataSource: RemoteSearchDataSource

    @Mock
    lateinit var apiService: SearchApiService

    override fun before() {
        dataSource = RemoteSearchDataSource(apiService)
    }

    @Test
    fun searchQueryEmpty(): Unit = runBlocking {
        dataSource.search("").apply {
            collectLatest {
                Assert.assertTrue(it is ApiResponse.Empty)
            }
        }
    }

    @Test
    fun searchResultEmpty(): Unit = runBlocking {
        Mockito.`when`(apiService.searchMulti(FakeDataSearch.FAKE_QUERY))
            .thenReturn(FakeDataSearch.pageResponseEmpty)
        dataSource.search(FakeDataSearch.FAKE_QUERY).apply {
            collectLatest {
                Assert.assertTrue(it is ApiResponse.Empty)
            }
        }
    }

    @Test
    fun searchResultSuccess(): Unit = runBlocking {
        Mockito.`when`(apiService.searchMulti(FakeDataSearch.FAKE_QUERY))
            .thenReturn(FakeDataSearch.pageResponse)
        dataSource.search(FakeDataSearch.FAKE_QUERY).apply {
            collectLatest {
                Assert.assertTrue(it is ApiResponse.Success)
            }
        }
    }

    @Test
    fun searchResultError(): Unit = runBlocking {
        Mockito.`when`(apiService.searchMulti(FakeDataSearch.FAKE_QUERY))
            .thenThrow(FakeDataSearch.exception)
        dataSource.search(FakeDataSearch.FAKE_QUERY).apply {
            collectLatest {
                Assert.assertTrue(it is ApiResponse.Error)
            }
        }
    }
}