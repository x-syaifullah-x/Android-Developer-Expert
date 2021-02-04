package com.android.developer.expert.core.domain.repository

import androidx.paging.PagingData
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.model.Type
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getMovieFavorite(): Flow<PagingData<ItemModel>>

    fun getTvFavorite(): Flow<PagingData<ItemModel>>

    suspend fun setFavorite(type: Type<*>, isFavorite: Boolean): Int
}