package com.android.developer.expert.presentation.favorite

import id.xxx.the.movie.db.domain.model.Type

class FavoriteTvFragment : FavoriteMovieFragment() {

    override fun getData() = viewModel.tvFav

    override fun getType(id: Int): Type<Int> = Type.Tv(id)
}