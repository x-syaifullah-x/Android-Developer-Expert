package com.android.developer.expert.base.adapter.paging

import android.annotation.SuppressLint
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.android.developer.expert.base.adapter.ViewHolder
import com.android.developer.expert.core.domain.model.base.IModel

abstract class BasePagingDataAdapter<ItemBinding : ViewBinding, Model : IModel<*>> :
    PagingDataAdapter<Model, ViewHolder<ItemBinding>>(Diff<Model>()) {

    @SuppressLint("DiffUtilEquals")
    private class Diff<Model : IModel<*>> : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(
            oldItem: Model, newItem: Model
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Model, newItem: Model
        ) = oldItem == newItem
    }
}