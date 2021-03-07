package com.android.developer.expert.presentation.detail

import android.R.drawable.ic_menu_add
import android.R.drawable.ic_menu_delete
import android.graphics.Color.RED
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.MenuItem
import android.widget.RatingBar
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.android.developer.expert.R
import com.android.developer.expert.R.string.*
import com.android.developer.expert.base.BaseActivity
import com.android.developer.expert.databinding.ActivityDetailBinding
import com.android.developer.expert.domain.model.Type
import com.android.developer.expert.domain.model.base.IDetailModel
import com.android.developer.expert.domain.model.getTheMovieType
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import id.xxx.base.binding.delegate.viewBinding
import id.xxx.base.extension.setHomeButton
import id.xxx.base.extension.setVisibleWithCircularReveal
import id.xxx.base.extension.show
import id.xxx.base.extension.startPostponedEnterTransition
import kotlin.properties.Delegates

abstract class DetailActivity : BaseActivity<ActivityDetailBinding>(),
    OnOffsetChangedListener, OnRefreshListener {

    companion object {
        const val DATA_EXTRA = "DATA_EXTRA_FAV"
    }

    private var typeExtra: Type<Int> by Delegates.notNull()
    private var typeExtraId: Int by Delegates.notNull()
    private var isStartTransition = true
        get() = field.apply { isStartTransition = false }

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }
    private val swipeRefresh by lazy { binding.swipeRefresh }
    private val fab by lazy { binding.fab }
    private val includeCollapsing by lazy { binding.includeCollapsingToolbar }
    private val progressBar by lazy { includeCollapsing.progressBar }
    private val collapsing by lazy { includeCollapsing.collapsing }
    private val banner by lazy { includeCollapsing.banner }
    private val poster by lazy { includeCollapsing.poster }
    private val includeBottomSheet by lazy { binding.includeBottomSheetConfirm }
    private val bottomSheetBehavior by lazy { from(includeBottomSheet.bottomSheet) }

    override val binding by viewBinding(ActivityDetailBinding::inflate)

    override fun isInject() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()
        setSupportActionBar(includeCollapsing.toolbar)
        supportActionBar.setHomeButton(isHomeButtonEnabled = true, isDisplayHomeAsUpEnabled = true)
        setStatBottomSheet(STATE_HIDDEN)
        typeExtra = intent.getTheMovieType()
        setDetailFragment(typeExtra)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val ctMinimumHeight = ViewCompat.getMinimumHeight(collapsing)
        val ctCurrentHeight = collapsing.height + verticalOffset
        binding.swipeRefresh.isEnabled = (ctCurrentHeight == appBarLayout.height)
        supportActionBar.show(ctCurrentHeight == ctMinimumHeight)
    }

    override fun onRefresh() {
        swipeRefresh.isRefreshing = false
        progressBar.isVisible = true
        viewModel.apply { refresh(typeExtraId) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                removePosterTransitionName()
                supportFinishAfterTransition(); true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        removePosterTransitionName()
        if (fab.isVisible) fab.isVisible = false
        if (isStatBottomSheet(STATE_EXPANDED)) {
            setStatBottomSheet(STATE_HIDDEN)
        } else {
            super.onBackPressed()
        }
    }

    private fun setStatBottomSheet(stat: Int) {
        if (fab.drawable != null)
            fab.setVisibleWithCircularReveal(stat == STATE_HIDDEN)
        bottomSheetBehavior.state = stat
    }

    private fun setDetailFragment(type: Type<Int>) {
        val fragment = when (type) {
            is Type.Movie -> DetailMovieFragment().apply { typeExtraId = type.id }
            is Type.Tv -> DetailTvFragment().apply { typeExtraId = type.id }
        }
        supportFragmentManager.commit {
            replace(R.id.fragment, fragment)
        }.apply { viewModel.refresh(typeExtraId) }
        binding.appBar.addOnOffsetChangedListener(this)
        swipeRefresh.setOnRefreshListener(this)
    }

    private fun setFabAddDelete(isFavorite: Boolean) {
        val icon = if (isFavorite) ic_menu_delete else ic_menu_add
        val imageDrawable = ContextCompat.getDrawable(baseContext, icon)
        if (!fab.isVisible) fab.setVisibleWithCircularReveal(true)
        fab.setImageDrawable(imageDrawable)
        fab.setOnClickListener {
            setStatBottomSheet(STATE_EXPANDED)
            val addOrDel = getString(if (isFavorite) Delete else Add)
            includeBottomSheet.confirm.text = getString(confirm, addOrDel)
            includeBottomSheet.btnNo.setOnClickListener { setStatBottomSheet(STATE_HIDDEN) }
            includeBottomSheet.btnYes.setOnClickListener {
                setStatBottomSheet(STATE_HIDDEN)
                viewModel.setFavorite(typeExtra, !isFavorite)
            }
        }
    }

    private fun isDestFromFav() = intent.getBooleanExtra(DATA_EXTRA, false)

    private fun removePosterTransitionName() {
        if (poster.drawable == null) banner.transitionName = null
    }

    fun isStatBottomSheet(stat: Int) = (bottomSheetBehavior.state == stat)

    fun <T : IDetailModel> onReceiveData(data: T?, isError: Boolean, errorMessage: String?) {

        poster.startPostponedEnterTransition(isStartTransition)

        data?.apply {
            if (isDestFromFav() && !isFavorite) onBackPressed()

            val rating: RatingBar = findViewById(R.id.user_score_content)
            setPoster(urlPosterPath, poster)
            setBanner(
                backdropPath, progressBar, collapsing, rating, banner, includeCollapsing.cardView
            )
        }
        collapsing.title = data?.title ?: errorMessage
        collapsing.setExpandedTitleColor(if (isError) RED else TRANSPARENT)

        if (data?.isValid == true) setFabAddDelete(data.isFavorite)

        if (isError) {
            banner.setImageResource(R.drawable.detail_error)
            progressBar.isVisible = false
        }
    }
}