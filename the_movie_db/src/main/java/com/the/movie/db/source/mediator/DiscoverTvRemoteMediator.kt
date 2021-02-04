package com.the.movie.db.source.mediator

import androidx.paging.ExperimentalPagingApi
import com.the.movie.db.source.local.DiscoverTvDataSource
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvResultEntity
import com.the.movie.db.source.remote.RemoteTvDataSource
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.TvResult
import javax.inject.Inject

@ExperimentalPagingApi
class DiscoverTvRemoteMediator @Inject constructor(
    private val remoteDataSource: RemoteTvDataSource,
    private val discoverDataSource: DiscoverTvDataSource,
) : DiscoverRemoteMediator<Int, DiscoverTvResultEntity, TvResult>() {

    override suspend fun nextPage() =
        discoverDataSource.getNextPage()

    override suspend fun fetch(page: Int) =
        remoteDataSource.getDiscover(page)

    override suspend fun clearDiscover() =
        discoverDataSource.clearDiscover()

    override suspend fun saveFetch(pageResponse: PageResponse<TvResult>) =
        discoverDataSource.insert(pageResponse)
}