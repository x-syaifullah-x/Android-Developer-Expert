package com.android.developer.expert.presentation.adapter

import android.view.LayoutInflater.from
import android.view.ViewGroup
import com.android.developer.expert.base.adapter.ViewHolder
import com.android.developer.expert.base.adapter.paging.BasePagingDataAdapter
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.databinding.ItemDiscoverBinding

class ItemDiscoverAdapter(
    private val onItemClick: (ItemDiscoverBinding, ItemModel) -> Unit = { _, _ -> }
) : BasePagingDataAdapter<ItemDiscoverBinding, ItemModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDiscoverBinding.inflate(from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder<ItemDiscoverBinding>, position: Int) {
        val data = getItem(position) ?: return
        val binding = holder.binding
        binding.includeItem.data = data
        binding.root.setOnClickListener { onItemClick(binding, data) }
    }
}