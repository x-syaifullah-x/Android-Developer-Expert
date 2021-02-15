package com.android.developer.expert.base.adapter

import androidx.viewbinding.ViewBinding
import com.base.binding.adapter.binding.HolderWithBinding

open class ViewHolder<T : ViewBinding>(binding: T) : HolderWithBinding<T>(binding)