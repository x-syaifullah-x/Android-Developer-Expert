package com.android.developer.expert.base.adapter.paging

import androidx.viewbinding.ViewBinding
import com.android.developer.expert.base.adapter.ViewHolder
import id.xxx.base.domain.adapter.BaseAdapter
import id.xxx.the.movie.db.domain.model.base.IModel

abstract class BasePagingDataAdapter<ItemBinding : ViewBinding, Model : IModel<*>> :
    BaseAdapter.WithPaging3<Model, ItemBinding, ViewHolder<ItemBinding>>()