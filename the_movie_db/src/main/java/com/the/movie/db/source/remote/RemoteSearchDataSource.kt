package com.the.movie.db.source.remote

import com.the.movie.db.source.remote.network.ApiResponse
import com.the.movie.db.source.remote.network.services.SearchApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSearchDataSource @Inject constructor(private val apiService: SearchApiService) {

    fun search(query: String) = flow {
        val result = ApiResponse.fetch {
            if (query.isEmpty()) return@fetch ApiResponse.Empty
            val pageResponse = apiService.searchMulti(query)
            if (pageResponse.results.isNotEmpty()) ApiResponse.Success(pageResponse) else ApiResponse.Empty
        }
        emit(result)
    }
}