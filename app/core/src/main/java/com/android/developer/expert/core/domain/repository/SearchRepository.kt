package com.android.developer.expert.core.domain.repository

import com.android.developer.expert.core.domain.model.Resource
import com.android.developer.expert.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    companion object {
        const val MEDIA_TYPE_MOVIE = "movie"
        const val MEDIA_TYPE_TV = "tv"
    }

    fun search(query: String): Flow<Resource<List<SearchModel>>>
}