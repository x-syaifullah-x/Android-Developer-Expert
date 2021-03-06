package com.android.developer.expert.core.data.repository

import com.android.developer.expert.core.data.mapper.toSearchModel
import com.android.developer.expert.core.data.mediator.networkBound
import com.android.developer.expert.core.domain.repository.SearchRepository
import com.android.developer.expert.core.domain.repository.SearchRepository.Companion.MEDIA_TYPE_MOVIE
import com.android.developer.expert.core.domain.repository.SearchRepository.Companion.MEDIA_TYPE_TV
import com.the.movie.db.mapper.toSearchEntity
import com.the.movie.db.source.local.SearchDataSource
import com.the.movie.db.source.remote.RemoteSearchDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSearchDataSource: RemoteSearchDataSource,
    private val searchDataSource: SearchDataSource
) : SearchRepository {

    override fun search(query: String) = networkBound(
        loadFromDB = {
            searchDataSource.search(query)
                .map { it.map { data -> data.toSearchModel() } }
        },
        fetch = { remoteSearchDataSource.search(query) },
        saveFetchResult = {
            val searchEntity = it.results
                .filter { type -> type.mediaType == MEDIA_TYPE_MOVIE || type.mediaType == MEDIA_TYPE_TV }
                .map { data -> data.toSearchEntity() }
            searchDataSource.insert(searchEntity)
        }
    )
}