package com.android.developer.expert.presentation.search

import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT
import com.DetailActivity
import com.android.developer.expert.R
import com.android.developer.expert.R.string.*
import com.android.developer.expert.base.BaseActivity
import com.android.developer.expert.core.domain.model.Resource
import com.android.developer.expert.core.domain.model.SearchModel
import com.android.developer.expert.core.domain.model.Type
import com.android.developer.expert.core.domain.model.putType
import com.android.developer.expert.core.domain.repository.SearchRepository.Companion.MEDIA_TYPE_TV
import com.android.developer.expert.databinding.ActivitySearchBinding
import com.android.developer.expert.presentation.adapter.ItemSearchAdapter
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivity
import com.base.extension.setHomeButton
import com.base.extension.setVisibleWithCircularReveal

abstract class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels { viewModelFactory }

    private val searchAdapter = ItemSearchAdapter { _, model -> onItemClick(model) }

    private val content by lazy { binding.content }

    override val binding: ActivitySearchBinding by viewBinding(ActivitySearchBinding::inflate)

    override fun isInject() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar.setHomeButton(isHomeButtonEnabled = true, isDisplayHomeAsUpEnabled = true)

        content.icEmpty.setVisibleWithCircularReveal(true)
        content.textMessage.setVisibleWithCircularReveal(true)

        content.searchField.addTextChangedListener { viewModel.send(it.toString()) }

        content.rvSearch.apply {
            setHasFixedSize(true)
            searchAdapter.stateRestorationPolicy = PREVENT
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        viewModel.searchResult.observe(this) {
            when (it) {
                is Resource.Empty -> submit(null, getString(empty))
                is Resource.Loading -> submit(null, getString(loading), true)
                is Resource.Error -> submit(it.data, getString(error))
                is Resource.Success -> with(it.data) {
                    val msg = getString(if (isEmpty()) empty else success)
                    submit(this, msg)
                }
            }
        }
    }

    private fun submit(data: List<SearchModel>?, msg: String? = null, isLoad: Boolean = false) {
        content.textMessage.text = msg
        content.progress.isVisible = isLoad

        if (!isLoad) {
            val icon = if (data.isNullOrEmpty()) R.drawable.ic_filed else R.drawable.ic_check
            content.groupIncludeEmpty.isVisible = data.isNullOrEmpty()
            content.searchDone.setImageDrawable(ContextCompat.getDrawable(this, icon))
            searchAdapter.submitData(lifecycle, PagingData.from(data ?: listOf()))
            content.rvSearch.scrollToPosition(0)
        }
    }

    private fun onItemClick(model: SearchModel) {
        openActivity<DetailActivity> {
            val id = model.id ?: return
            val type = if (model.mediaType == MEDIA_TYPE_TV) Type.Tv(id) else Type.Movie(id)
            putType(type)
        }
    }
}