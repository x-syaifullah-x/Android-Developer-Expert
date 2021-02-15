package com.android.developer.expert.presentation.adapter

import android.view.LayoutInflater.from
import android.view.ViewGroup
import com.android.developer.expert.base.adapter.ViewHolder
import com.android.developer.expert.base.adapter.paging.BasePagingDataAdapter
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.databinding.ItemRecommendationBinding

class ItemRecommendationAdapter(
    private val onItemClick: (ItemRecommendationBinding, ItemModel) -> Unit = { _, _ -> }
) : BasePagingDataAdapter<ItemRecommendationBinding, ItemModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemRecommendationBinding.inflate(from(parent.context), parent, false))

    override fun onBindViewHolder(hol: ViewHolder<ItemRecommendationBinding>, pos: Int) {
        val model = getItem(pos) ?: return
        val binding = hol.binding
        binding.includeItem.data = model
        binding.root.setOnClickListener { onItemClick(binding, model) }
    }
}