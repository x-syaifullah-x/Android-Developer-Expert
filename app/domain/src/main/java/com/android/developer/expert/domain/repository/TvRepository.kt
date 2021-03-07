package com.android.developer.expert.domain.repository

import androidx.paging.PagingData
import com.android.developer.expert.domain.model.DetailTvModel
import com.android.developer.expert.domain.model.ItemModel
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TvRepository {

    fun getDiscoverTv(): Flow<PagingData<ItemModel>>

    fun getTv(id: Int): Flow<Resource<DetailTvModel>>

}