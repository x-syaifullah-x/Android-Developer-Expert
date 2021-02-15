package com.android.developer.expert.presentation

import android.content.res.Configuration
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DetailActivity
import com.android.developer.expert.R.layout.fragment_main
import com.android.developer.expert.R.string.*
import com.android.developer.expert.base.BasePagingDataFragment
import com.android.developer.expert.base.adapter.paging.BasePagingDataAdapter
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.model.Type
import com.android.developer.expert.core.domain.model.putType
import com.android.developer.expert.databinding.FragmentMainBinding
import com.android.developer.expert.databinding.ItemDiscoverBinding
import com.android.developer.expert.presentation.adapter.FooterAdapter
import com.android.developer.expert.presentation.adapter.ItemDiscoverAdapter
import com.base.binding.delegate.viewBinding
import com.base.extension.createPair
import com.base.extension.openActivity
import com.base.extension.setVisibleWithCircularReveal

abstract class MainFragment : BasePagingDataFragment<ItemModel>(fragment_main) {

    companion object {
        private const val FOUR_ROW = 4
        private const val TWO_ROW = 2
        private const val ONE_ROW = 1
        private const val TOP_POSITION = 0
    }

    private val itemAdapter = ItemDiscoverAdapter { v, model -> onItemClick(v, model) }

    private val footerAdapter = FooterAdapter { getAdapter().retry() }

    private val shimmer get() = binding.shimmerRecyclerView

    private val binding by viewBinding<FragmentMainBinding> {
        recyclerView.adapter = null
    }

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val footerIsShow = (footerAdapter.itemCount == ONE_ROW)
            val isTrue = position >= getAdapter().itemCount && footerIsShow
            val spanCount = getRecyclerView().girdLayout.spanCount
            return if (isTrue) spanCount else ONE_ROW
        }
    }

    override fun getRecyclerView() = binding.recyclerView

    override fun setRecyclerView(recyclerView: RecyclerView, adapter: BasePagingDataAdapter<*, *>) {
        recyclerView.apply {
            this.adapter = adapter.withLoadStateFooter(footerAdapter)
            val isLandscape =
                (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            val spanCount = if (isLandscape) FOUR_ROW else TWO_ROW
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            if (layoutManager is GridLayoutManager) girdLayout.spanSizeLookup = spanSizeLookup
            val statPosition = getStatScroll()[keyStatScroll()] ?: TOP_POSITION
            scrollToPosition(statPosition)
        }
    }

    final override fun handleLoadListenerFirstLoadErrorDataNotEmpty() {
        shimmer.hideShimmerAdapter()
    }

    final override fun handleLoadListenerDataEmpty() {
        shimmer.hideShimmerAdapter()
        binding.icEmpty.setVisibleWithCircularReveal(true)
        binding.textMessage.setVisibleWithCircularReveal(true)
    }

    final override fun handleLoadListenerFirstLoading() {
        if (getAdapter().itemCount == 0) {
            shimmer.isVisible = true
            shimmer.showShimmerAdapter()
        }
    }

    final override fun handleLoadListenerNotEmpty() {
        shimmer.hideShimmerAdapter()
    }

    final override fun getAdapter() = itemAdapter

    final override fun handleLoadListenerFirstLoadErrorDataEmpty() {
        errorDestination()?.apply {
            if (isRetry) getAdapter().retry() else findNavController().navigate(this)
        }
    }

    protected open fun onItemClick(viewBinding: ItemDiscoverBinding, model: ItemModel) {
        val view = viewBinding.includeItem
        val option = makeSceneTransitionAnimation(
            requireActivity(),
            view.posterPath.createPair(getString(banner_transition)),
            view.includeItemTitle.createPair(getString(title_transition)),
            view.rbUserScoreList.createPair(getString(score_transition)),
            view.textUserScorePercent.createPair(getString(percent_score_transition))
        )

        openActivity<DetailActivity>(option) { putType(getType(model.id)) }
    }

    protected abstract fun getType(id: Int): Type<Int>

    protected open fun errorDestination(): Int? = null
}