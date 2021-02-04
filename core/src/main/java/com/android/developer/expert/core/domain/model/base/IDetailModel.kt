package com.android.developer.expert.core.domain.model.base

import com.android.developer.expert.core.domain.model.GenreModel
import com.android.developer.expert.core.domain.model.ItemModel

interface IDetailModel : IModel<Int> {
    val backdropPath: String
    val originalLanguage: String
    val overview: String
    val urlPosterPath: String
    val title: String
    val voteAverage: Float
    val genres: List<GenreModel>
    val recommendation: List<ItemModel>
    val isFavorite: Boolean
}