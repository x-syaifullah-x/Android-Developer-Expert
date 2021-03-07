package com.android.developer.expert.domain.usecase

import androidx.paging.PagingData
import com.android.developer.expert.domain.model.*
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

@Suppress("SpellCheckingInspection")
interface Interactor {

    fun getDiscoverTv(): Flow<PagingData<ItemModel>>

    fun getTv(id: Int): Flow<Resource<DetailTvModel>>

    fun getTvFavorite(): Flow<PagingData<ItemModel>>

    fun getMovie(id: Int): Flow<Resource<DetailMovieModel>>

    fun getMovieDiscover(): Flow<PagingData<ItemModel>>

    fun getMovieFavorite(): Flow<PagingData<ItemModel>>

    suspend fun setFavorite(type: Type<*>, isFavorite: Boolean): Int

    fun search(query: String): Flow<Resource<List<SearchModel>>>
}