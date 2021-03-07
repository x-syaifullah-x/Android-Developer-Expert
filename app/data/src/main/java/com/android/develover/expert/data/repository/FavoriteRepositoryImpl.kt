package com.android.develover.expert.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.android.developer.expert.domain.model.Type
import com.android.developer.expert.domain.repository.FavoriteRepository
import com.android.develover.expert.data.mapper.toItemModel
import com.the.movie.db.source.local.MovieDataSource
import com.the.movie.db.source.local.TvDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val movie: MovieDataSource,
    private val tv: TvDataSource
) : FavoriteRepository {
    private val config = PagingConfig(pageSize = 20, enablePlaceholders = false)

    override fun getMovieFavorite() = Pager(
        config = config,
        pagingSourceFactory = { movie.getFavorite() }
    ).flow.map { it.map { data -> data.toItemModel(data.title) } }

    override fun getTvFavorite() = Pager(
        config = config,
        pagingSourceFactory = { tv.getFavorite() }
    ).flow.map { it.map { data -> data.toItemModel(data.name) } }

    override suspend fun setFavorite(type: Type<*>, isFavorite: Boolean) = when (type) {
        is Type.Movie -> movie.setFavorite(type.id, isFavorite)
        is Type.Tv -> tv.setFavorite(type.id, isFavorite)
    }
}