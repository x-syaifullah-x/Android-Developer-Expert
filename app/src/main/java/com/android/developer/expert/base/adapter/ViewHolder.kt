package com.android.developer.expert.base.adapter

import androidx.viewbinding.ViewBinding
import id.xxx.base.domain.adapter.HolderWithBinding

class ViewHolder<T : ViewBinding>(binding: T) : HolderWithBinding<T>(binding)