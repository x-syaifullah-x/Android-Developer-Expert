package com.android.developer.expert.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.android.developer.expert.base.adapter.ViewHolder
import com.android.developer.expert.databinding.ItemFooterBinding

class FooterAdapter(
    private val callbackRetry: () -> Unit
) : LoadStateAdapter<ViewHolder<ItemFooterBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(ItemFooterBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(
        holder: ViewHolder<ItemFooterBinding>,
        loadState: LoadState
    ) {
        holder.binding.also {
            it.footerProgressBar.isVisible = loadState is LoadState.Loading
            it.footerButton.isVisible = loadState is LoadState.Error
            it.footerButton.setOnClickListener { callbackRetry() }
        }
    }
}