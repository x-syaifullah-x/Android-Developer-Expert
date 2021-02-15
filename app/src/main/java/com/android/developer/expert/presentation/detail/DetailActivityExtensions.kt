package com.android.developer.expert.presentation.detail

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.palette.graphics.Palette
import com.GlideApp
import com.android.developer.expert.R
import com.android.developer.expert.R.color.design_default_color_primary
import com.android.developer.expert.extension.listener
import com.base.extension.setVisibleWithCircularReveal
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.appbar.CollapsingToolbarLayout
import jp.wasabeef.glide.transformations.BlurTransformation

fun DetailActivity.setBanner(
    backdoorPathUrl: String,
    progressBar: ProgressBar,
    collapsing: CollapsingToolbarLayout,
    ratingBar: RatingBar,
    banner: ImageView,
    cardView: CardView
) {
    GlideApp.with(this@setBanner)
        .load(backdoorPathUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(backdoorPathUrl))
        .apply(RequestOptions.bitmapTransform(BlurTransformation(20)))
        .error(R.drawable.detail_error)
        .listener(
            onLoadFailed = { progressBar.isVisible = false },
            onResourceReady = {
                val palette = Palette.Builder(this?.toBitmap() ?: return@listener).generate()
                val color = getColor(baseContext, design_default_color_primary)
                val dominantColor = palette.getDominantColor(color)
                window.statusBarColor = dominantColor
                collapsing.setContentScrimColor(dominantColor)
                ratingBar.progressDrawable.setTint(dominantColor)
                progressBar.isVisible = false
                if (!cardView.isVisible) {
                    cardView.setVisibleWithCircularReveal(true)
                }
            }
        ).into(banner)
}

fun DetailActivity.setPoster(url: String?, poster: ImageView) {
    url ?: return
    GlideApp.with(this@setPoster)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(url))
        .into(poster)
}