package com.android.developer.expert.domain.model

import com.android.developer.expert.domain.model.base.IModel

data class ItemModel(
    val name: String,
    val overview: String,
    val urlPosterPath: String,
    val voteAverage: Float,
    override val id: Int
) : IModel<Int>