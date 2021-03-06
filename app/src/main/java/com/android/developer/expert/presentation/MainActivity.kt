package com.android.developer.expert.presentation

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.SearchActivity
import com.android.developer.expert.R.id.*
import com.android.developer.expert.base.BaseActivity
import com.android.developer.expert.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import id.xxx.base.binding.delegate.viewBinding
import id.xxx.base.extension.disableDisplayShowAndHomeAsUp
import id.xxx.base.extension.disableMenuChecked
import id.xxx.base.extension.openActivity
import id.xxx.base.extension.show

abstract class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController by lazy { navHostFragment.navController }

    private val bottomNavigation by lazy { binding.bottomNavigationView }

    override val binding by viewBinding(ActivityMainBinding::inflate)

    override fun isInject() = true

    override fun getIdNavHost() = nav_host_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        setupWithNavController(bottomNavigation, navController)

        val appBarConfiguration = Builder(fragment_movie, fragment_tv, fragment_feature)
        setupActionBarWithNavController(navController, appBarConfiguration.build())

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.disableMenuChecked()
            addOnDestinationChangedListener(destination)
        }

        binding.fabSearch.setOnClickListener { openActivity<SearchActivity>() }
    }

    override fun onSupportNavigateUp() = super.onSupportNavigateUp() || navController.navigateUp()

    private fun addOnDestinationChangedListener(destination: NavDestination) {
        val isFragmentError = destination.id == fragment_error
        val isFragmentFavorite = destination.id == fragment_favorite

        binding.fabSearch.isVisible = !isFragmentError
        setScrollFlags(!isFragmentFavorite)
        bottomNavigation.show(!(isFragmentError || isFragmentFavorite))

        disableDisplayShowAndHomeAsUp(destination, fragment_error, fragment_favorite)
    }

    private fun setScrollFlags(isScroll: Boolean) {
        val toolbar = binding.toolbar.layoutParams as AppBarLayout.LayoutParams
        val scrollFlags =
            if (isScroll) SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS else SCROLL_FLAG_NO_SCROLL
        toolbar.scrollFlags = scrollFlags
    }
}