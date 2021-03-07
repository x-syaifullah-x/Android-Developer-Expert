package com.android.developer.expert.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.expert.base.adapter.paging.BasePagingDataAdapter
import com.android.developer.expert.base.adapter.paging.IStatPagingData
import com.android.developer.expert.domain.model.base.IModel

abstract class BasePagingDataFragment<ItemModel : IModel<Int>>(
    @LayoutRes private val layout: Int
) : Fragment(layout), IStatPagingData<ItemModel> {

    companion object {
        const val IS_ERROR = "IS_ERROR"
    }

    protected val RecyclerView.girdLayout
        get() = if (layoutManager is GridLayoutManager) {
            layoutManager as GridLayoutManager
        } else {
            error("RecyclerView.layoutManager != GridLayoutManager")
        }

    protected val isRetry: Boolean
        get() {
            val isRetry = arguments?.getBoolean(IS_ERROR) ?: false
            arguments?.putAll(bundleOf(IS_ERROR to null)) /* reset argument IS_ERROR */
            return isRetry
        }

    protected open fun isEnableHandleLoadStatListener(): Boolean = true

    protected abstract fun getStatScroll(): HashMap<String, Int>

    protected abstract fun getRecyclerView(): RecyclerView

    protected abstract fun saveStatScroll(scrollPosition: Int, key: String)

    protected abstract fun setRecyclerView(
        recyclerView: RecyclerView, adapter: BasePagingDataAdapter<*, *>
    )

    protected abstract fun getData(): LiveData<PagingData<ItemModel>>

    protected fun keyStatScroll(): String = this::class.java.name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(getRecyclerView(), getAdapter())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getData().observe(viewLifecycleOwner) {
            getAdapter().submitData(viewLifecycleOwner.lifecycle, it)
        }

        if (isEnableHandleLoadStatListener())
            getAdapter().addLoadStateListener { handleLoadStatListener(it) }
    }

    override fun onPause() {
        super.onPause()
        getRecyclerView().apply {
            if (layoutManager is LinearLayoutManager) {
                val pos = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                saveStatScroll(pos, keyStatScroll())
            }
        }
    }
}