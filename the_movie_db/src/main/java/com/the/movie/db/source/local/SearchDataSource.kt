package com.the.movie.db.source.local

import com.the.movie.db.source.local.dao.SearchDao
import com.the.movie.db.source.local.entity.search.SearchEntity
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchDataSource @Inject constructor(private val dao: SearchDao) {
    fun search(query: String) =
        if (query.isEmpty()) flowOf(listOf()) else dao.search(query)

    suspend fun insert(searchEntity: List<SearchEntity>) = dao.insert(searchEntity)
}