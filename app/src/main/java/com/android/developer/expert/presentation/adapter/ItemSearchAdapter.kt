package com.android.developer.expert.presentation.adapter

import android.view.LayoutInflater.from
import android.view.ViewGroup
import com.android.developer.expert.base.adapter.ViewHolder
import com.android.developer.expert.base.adapter.paging.BasePagingDataAdapter
import com.android.developer.expert.core.domain.model.SearchModel
import com.android.developer.expert.databinding.ItemSearchBinding

open class ItemSearchAdapter(
    private val onItemClick: (ItemSearchBinding, SearchModel) -> Unit = { _, _ -> }
) : BasePagingDataAdapter<ItemSearchBinding, SearchModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemSearchBinding.inflate(from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder<ItemSearchBinding>, position: Int) {
        val data = getItem(position) ?: return
        val binding = holder.binding
        binding.data = data
        binding.root.setOnClickListener { onItemClick(binding, data) }
    }
}