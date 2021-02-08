package com.the.movie.db.data.fake.local

import com.the.movie.db.source.local.entity.discover.movie.DiscoverMovieEntity
import com.the.movie.db.source.local.entity.discover.tv.DiscoverTvEntity

object FakeData {
    fun getDiscoverMovieEntity(totalPage: Int) = DiscoverMovieEntity(
        totalPages = 1,
        totalResults = 1,
        page = totalPage
    )

    fun getDiscoverTvEntity(totalPage: Int) = DiscoverTvEntity(
        totalPages = 1,
        totalResults = 1,
        page = totalPage
    )
}