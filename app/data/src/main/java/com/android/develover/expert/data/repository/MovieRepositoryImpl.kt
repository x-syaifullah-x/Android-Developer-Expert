package com.android.develover.expert.data.repository

import androidx.paging.*
import com.android.developer.expert.domain.model.ItemModel
import com.android.developer.expert.domain.repository.MovieRepository
import com.android.develover.expert.data.mapper.toDetailMovieModel
import com.android.develover.expert.data.mapper.toItemModel
import com.android.develover.expert.data.mediator.networkBound
import com.the.movie.db.mapper.toMovieEntity
import com.the.movie.db.mapper.toMovieGenreEntity
import com.the.movie.db.mapper.toPageEmbedded
import com.the.movie.db.source.local.MovieDataSource
import com.the.movie.db.source.mediator.DiscoverMovieRemoteMediator
import com.the.movie.db.source.remote.RemoteMovieDataSource
import com.the.movie.db.source.remote.response.model.MovieWithRecommendation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepositoryImpl @Inject constructor(
    private val remoteMovie: RemoteMovieDataSource,
    private val movie: MovieDataSource,
    private val discoverMovieRemoteMediator: DiscoverMovieRemoteMediator
) : MovieRepository {

    override fun getMovie(id: Int) = networkBound(
        loadFromDB = { movie.getMovie(id).map { it.toDetailMovieModel() } },
        fetch = { remoteMovie.getResponseWithRecommendation<MovieWithRecommendation>(id) },
        saveFetchResult = {
            val movieEntity = it.toMovieEntity()
            val genres = it.genres.map { data -> data.toMovieGenreEntity() }
            val recommendations = it.recommendations.map { data -> data.toPageEmbedded() }
            movie.insert(movieEntity, genres, recommendations)
        }
    )

    override fun getDiscoverMovie(): Flow<PagingData<ItemModel>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = discoverMovieRemoteMediator,
        pagingSourceFactory = { movie.getMovieResult() }
    ).flow.map {
        it.map { data -> data.toItemModel(data.title) }
    }
}