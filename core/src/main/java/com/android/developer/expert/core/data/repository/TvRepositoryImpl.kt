package com.android.developer.expert.core.data.repository

import androidx.paging.*
import com.android.developer.expert.core.data.mapper.toDetailTvModel
import com.android.developer.expert.core.data.mapper.toItemModel
import com.android.developer.expert.core.data.mediator.networkBound
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.repository.TvRepository
import com.the.movie.db.mapper.toPageEmbedded
import com.the.movie.db.mapper.toTvEntity
import com.the.movie.db.mapper.toTvGenreEntity
import com.the.movie.db.source.local.TvDataSource
import com.the.movie.db.source.mediator.DiscoverTvRemoteMediator
import com.the.movie.db.source.remote.RemoteTvDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class TvRepositoryImpl @Inject constructor(
    private val remoteTv: RemoteTvDataSource,
    private val tv: TvDataSource,
    private val discoverTvRemoteMediator: DiscoverTvRemoteMediator
) : TvRepository {

    override fun getDiscoverTv(): Flow<PagingData<ItemModel>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = discoverTvRemoteMediator,
        pagingSourceFactory = { tv.getTvResult() }
    ).flow.map { it.map { data -> data.toItemModel(data.name) } }

    override fun getTv(id: Int) = networkBound(
        fetch = { remoteTv.getTvWithTvRecommendation(id) },
        loadFromDB = { tv.getTv(id).map { it.toDetailTvModel() } },
        saveFetchResult = {
            val tvEntity = it.toTvEntity()
            val genres = it.genres.map { data -> data.toTvGenreEntity() }
            val recommendations = it.recommendations.map { data -> data.toPageEmbedded() }
            tv.insert(tvEntity, genres, recommendations)
        }
    )
}