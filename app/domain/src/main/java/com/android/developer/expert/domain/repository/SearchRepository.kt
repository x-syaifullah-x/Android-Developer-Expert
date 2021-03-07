package com.android.developer.expert.domain.repository

import com.android.developer.expert.domain.model.SearchModel
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    companion object {
        const val MEDIA_TYPE_MOVIE = "movie"
        const val MEDIA_TYPE_TV = "tv"
    }

    fun search(query: String): Flow<Resource<List<SearchModel>>>
}