package com.the.movie.db.source.local

import androidx.room.withTransaction
import com.the.movie.db.mapper.toDiscoverMovieResultEntity
import com.the.movie.db.source.local.base.IDiscoverDataSource
import com.the.movie.db.source.local.dao.MovieDao
import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
import com.the.movie.db.source.remote.response.PageResponse
import com.the.movie.db.source.remote.response.model.MovieResult
import javax.inject.Inject

class DiscoverMovieDataSource @Inject constructor(
    private val dao: MovieDao
) : IDiscoverDataSource<DiscoverMovieEntity, MovieResult> {
    override suspend fun clearDiscover() = dao.clearDiscoverMovie()

    override suspend fun getPreviousPage() = dao.getLastPage()?.run {
        if (page == 1) null else page.minus(1)
    }

    override suspend fun getCurrentPage() = dao.getLastPage()

    override suspend fun getNextPage() = dao.getLastPage()?.run {
        if (page == totalPages) null else page.plus(1)
    }

    override suspend fun insert(data: PageResponse<MovieResult>) = dao.db.withTransaction {
        val discoverMovie = DiscoverMovieEntity(
            page = data.page, totalPages = data.totalPages, totalResults = data.totalResults
        )
        val discoverMovieResults = data.results
            .map { it.toDiscoverMovieResultEntity(data.page) }
        val resultDiscoverMovie = dao.insert(discoverMovie).toInt() != 0
        val resultDiscoverMovieResult = dao.insertDiscoverResults(discoverMovieResults).contains(0)
        return@withTransaction resultDiscoverMovie && resultDiscoverMovieResult
    }
}