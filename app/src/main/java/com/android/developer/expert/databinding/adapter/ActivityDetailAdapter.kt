package com.android.developer.expert.databinding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.developer.expert.core.domain.model.GenreModel

object ActivityDetailAdapter {

    @JvmStatic
    @BindingAdapter("set_genres")
    fun setGenres(view: TextView, genres: List<GenreModel>?) = genres?.run {
        view.text = StringBuilder().apply {
            genres.forEachIndexed { index, value ->
                append(if (index != genres.size - 1) "${value.name}, " else value.name)
            }
        }.toString()
    }
}