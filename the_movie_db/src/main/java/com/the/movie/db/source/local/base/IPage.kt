package com.the.movie.db.source.local.base

interface IPage {
    val page: Int
    val totalPages: Int
    val totalResults: Int
}