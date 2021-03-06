package com.android.developer.expert.base.adapter

import androidx.viewbinding.ViewBinding
import id.xxx.base.domain.adapter.HolderWithBinding

open class ViewHolder<T : ViewBinding>(binding: T) : HolderWithBinding<T>(binding)