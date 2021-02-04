package com.android.developer.expert.databinding.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.GlideApp
import com.android.developer.expert.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey

object ItemAdapter {
    @JvmStatic
    @BindingAdapter("set_image")
    fun setImage(view: AppCompatImageView, url: String?) {
        url?.apply {
            GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.ic_item_image_placeholder)
                .error(R.drawable.ic_item_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(ObjectKey(url))
                .skipMemoryCache(false)
                .into(view)
        }
    }
}