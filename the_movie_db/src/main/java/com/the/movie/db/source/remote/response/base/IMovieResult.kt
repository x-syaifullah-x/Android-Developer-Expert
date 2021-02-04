package com.the.movie.db.source.remote.response.base

interface IMovieResult : IResult {
    val originalTitle: String?
    val title: String?
}