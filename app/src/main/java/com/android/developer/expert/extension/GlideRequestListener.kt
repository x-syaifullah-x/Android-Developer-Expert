package com.android.developer.expert.extension

import com.GlideRequest
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline fun <reified T> GlideRequest<T>.listener(
    isOnLoadFailed: Boolean = false,
    isOnResourceReady: Boolean = false,
    crossinline onLoadFailed: GlideException?.() -> Unit = {},
    crossinline onResourceReady: T?.() -> Unit = {}
): GlideRequest<T> = listener(
    object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            if (e != null) onLoadFailed(e)
            return isOnLoadFailed
        }

        override fun onResourceReady(
            resource: T?,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady(resource)
            return isOnResourceReady
        }
    }
)