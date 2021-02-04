package com.android.developer.expert.core.domain.repository

import androidx.paging.PagingData
import com.android.developer.expert.core.domain.model.DetailMovieModel
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(id: Int): Flow<Resource<DetailMovieModel>>

    fun getDiscoverMovie(): Flow<PagingData<ItemModel>>
}