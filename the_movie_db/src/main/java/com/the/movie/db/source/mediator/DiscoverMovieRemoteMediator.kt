package com.the.movie.db.source.mediator

import androidx.paging.ExperimentalPagingApi
import com.the.movie.db.source.local.DiscoverMovieDataSource
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieResultEntity
import com.the.movie.db.source.remote.RemoteMovieDataSource
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import javax.inject.Inject

@ExperimentalPagingApi
class DiscoverMovieRemoteMediator @Inject constructor(
    private val remoteDataSource: RemoteMovieDataSource,
    private val discoverDataSource: DiscoverMovieDataSource,
) : DiscoverRemoteMediator<Int, DiscoverMovieResultEntity, MovieResult>() {

    override suspend fun nextPage() =
        discoverDataSource.getNextPage()

    override suspend fun fetch(page: Int) =
        remoteDataSource.getDiscover(page)

    override suspend fun clearDiscover() =
        discoverDataSource.clearDiscover()

    override suspend fun saveFetch(pageResponse: PageResponse<MovieResult>) =
        discoverDataSource.insert(pageResponse)
}