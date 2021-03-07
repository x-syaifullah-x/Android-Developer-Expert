package com.android.developer.expert.presentation.movie

import androidx.navigation.navGraphViewModels
import com.android.developer.expert.R
import com.android.developer.expert.R.id.nav_graph_main
import com.android.developer.expert.domain.model.Type
import com.android.developer.expert.domain.model.Type.Movie
import com.android.developer.expert.extension.factory
import com.android.developer.expert.presentation.MainActivity
import com.android.developer.expert.presentation.MainFragment
import com.android.developer.expert.presentation.MainViewModel
import kotlin.collections.set

open class MovieFragment : MainFragment() {

    protected val viewModel: MainViewModel by navGraphViewModels(nav_graph_main) { factory<MainActivity>() }

    override fun getData() = viewModel.discoverMovie

    override fun getStatScroll(): HashMap<String, Int> = viewModel.statScroll

    override fun saveStatScroll(scrollPosition: Int, key: String) {
        viewModel.statScroll[key] = scrollPosition
    }

    override fun errorDestination() = R.id.fragment_movie_to_fragment_error

    override fun getType(id: Int): Type<Int> = Movie(id)
}