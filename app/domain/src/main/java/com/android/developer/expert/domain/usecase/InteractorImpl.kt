package com.android.developer.expert.domain.usecase

import com.android.developer.expert.domain.model.Type
import com.android.developer.expert.domain.repository.FavoriteRepository
import com.android.developer.expert.domain.repository.MovieRepository
import com.android.developer.expert.domain.repository.SearchRepository
import com.android.developer.expert.domain.repository.TvRepository
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class InteractorImpl @Inject constructor(
    private val tv: TvRepository,
    private val movie: MovieRepository,
    private val favorite: FavoriteRepository,
    private val search: SearchRepository
) : Interactor {

    override fun getMovieDiscover() = movie.getDiscoverMovie()
    override fun getMovie(id: Int) = movie.getMovie(id)

    override fun getDiscoverTv() = tv.getDiscoverTv()
    override fun getTv(id: Int) = tv.getTv(id)

    override fun getMovieFavorite() = favorite.getMovieFavorite()
    override fun getTvFavorite() = favorite.getTvFavorite()

    override suspend fun setFavorite(type: Type<*>, isFavorite: Boolean) =
        favorite.setFavorite(type, isFavorite)

    override fun search(query: String) = search.search(query)
}