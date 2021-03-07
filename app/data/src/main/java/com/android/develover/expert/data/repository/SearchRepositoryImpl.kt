package com.android.develover.expert.data.repository

import com.android.developer.expert.domain.model.SearchModel
import com.android.developer.expert.domain.repository.SearchRepository
import com.android.developer.expert.domain.repository.SearchRepository.Companion.MEDIA_TYPE_MOVIE
import com.android.developer.expert.domain.repository.SearchRepository.Companion.MEDIA_TYPE_TV
import com.android.develover.expert.data.mapper.toSearchModel
import com.android.develover.expert.data.mediator.networkBound
import com.the.movie.db.mapper.toSearchEntity
import com.the.movie.db.source.local.SearchDataSource
import com.the.movie.db.source.remote.RemoteSearchDataSource
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSearchDataSource: RemoteSearchDataSource,
    private val searchDataSource: SearchDataSource
) : SearchRepository {

    override fun search(query: String): Flow<Resource<List<SearchModel>>> {
        return networkBound(
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
}