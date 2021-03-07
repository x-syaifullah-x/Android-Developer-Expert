package com.android.developer.expert.domain.repository

import androidx.paging.PagingData
import com.android.developer.expert.domain.model.DetailMovieModel
import com.android.developer.expert.domain.model.ItemModel
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(id: Int): Flow<Resource<DetailMovieModel>>

    fun getDiscoverMovie(): Flow<PagingData<ItemModel>>
}