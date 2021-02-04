package com.the.movie.db.source.remote.response.base

interface IResult {
    val posterPath: String?
    val id: Int
    val backdropPath: String?
    val voteAverage: Float
    val overview: String
    val originalLanguage: String
}