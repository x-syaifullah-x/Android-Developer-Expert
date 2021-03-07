package com.android.developer.expert.presentation.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.developer.expert.App
import com.android.developer.expert.presentation.favorite.R.string.tab_movies
import com.android.developer.expert.presentation.favorite.R.string.tab_tv
import com.android.developer.expert.presentation.favorite.databinding.FragmentFavoriteBinding
import com.android.developer.expert.presentation.favorite.di.component.DaggerFavoriteComponent
import id.xxx.base.binding.delegate.viewBinding
import id.xxx.base.di.multibinding.ViewModelFactory
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favComponent by lazy {
        val dataComponent = (requireActivity().application as App).dataComponent
        DaggerFavoriteComponent.factory().create(dataComponent)
    }

    private val binding by viewBinding<FragmentFavoriteBinding>()

    override fun onAttach(context: Context) {
        favComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(childFragmentManager)
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        binding.tabs.setupWithViewPager(viewPager)
    }

    private inner class PagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tab = intArrayOf(tab_movies, tab_tv)

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvFragment()
            else -> throw IllegalArgumentException("tab $position not found")
        }

        override fun getPageTitle(position: Int) = getString(tab[position])

        override fun getCount() = tab.size
    }
}