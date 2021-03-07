package com.android.developer.expert.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.PagingData.Companion.from
import androidx.recyclerview.widget.RecyclerView
import com.DetailActivity
import com.android.developer.expert.R
import com.android.developer.expert.R.string.detail_error
import com.android.developer.expert.R.string.detail_not_found
import id.xxx.the.movie.db.domain.model.ItemModel
import id.xxx.the.movie.db.domain.model.Type
import id.xxx.the.movie.db.domain.model.base.IDetailModel
import id.xxx.the.movie.db.domain.model.putType
import com.android.developer.expert.presentation.adapter.ItemRecommendationAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import id.xxx.base.domain.model.Resource
import id.xxx.base.extension.openActivityAndFinis

abstract class DetailFragment<Model : IDetailModel>(@LayoutRes layout: Int) : Fragment(layout) {

    private val activity by lazy { requireActivity() as DetailActivity }

    protected val viewModel by activityViewModels<DetailViewModel>()

    private val itemRecAdapter = ItemRecommendationAdapter { _, itemModel ->
        if (activity.isStatBottomSheet(STATE_EXPANDED)) return@ItemRecommendationAdapter
        onClickItemRecommendation(itemModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRecyclerView().apply {
            setHasFixedSize(true)
            addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen._18sdp)))
            adapter = itemRecAdapter
        }
    }

    protected abstract fun getRecyclerView(): RecyclerView

    private fun onClickItemRecommendation(model: ItemModel) {
        openActivityAndFinis<DetailActivity> { putType(getType(model.id)) }
    }

    protected abstract fun getType(id: Int): Type<Int>

    protected fun submitData(res: Resource<Model>) {
        return when (res) {
            is Resource.Loading -> submit()
            is Resource.Success -> submit(res.data)
            is Resource.Empty -> submit(errorMessage = getString(detail_not_found))
            is Resource.Error -> submit(res.data, getString(detail_error))
        }
    }

    @CallSuper
    protected open fun onSubmitData(data: Model?) {
        submitDataRecommendations(data?.recommendation)
    }

    private fun submit(data: Model? = null, errorMessage: String? = null) {
        onSubmitData(data)
        val isError = ((data == null) && (errorMessage != null))
        activity.onReceiveData(data, isError, errorMessage)
    }

    private fun submitDataRecommendations(recommendations: List<ItemModel>?) {
        itemRecAdapter.submitData(viewLifecycleOwner.lifecycle, from(recommendations ?: return))
    }
}