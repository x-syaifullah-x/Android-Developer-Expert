package com.android.developer.expert.presentation.tv

import com.android.developer.expert.R
import com.android.developer.expert.domain.model.Type
import com.android.developer.expert.presentation.movie.MovieFragment

class TvFragment : MovieFragment() {

    override fun getData() = viewModel.discoverTv

    override fun errorDestination() = R.id.fragment_tv_to_fragment_error

    override fun getType(id: Int) = Type.Tv(id)
}