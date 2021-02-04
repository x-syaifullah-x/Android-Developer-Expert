package com.android.developer.expert.core.domain.repository

import androidx.paging.PagingData
import com.android.developer.expert.core.domain.model.DetailTvModel
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TvRepository {

    fun getDiscoverTv(): Flow<PagingData<ItemModel>>

    fun getTv(id: Int): Flow<Resource<DetailTvModel>>

}