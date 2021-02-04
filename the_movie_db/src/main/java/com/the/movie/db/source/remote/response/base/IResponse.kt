package com.the.movie.db.source.remote.response.base

import com.the.movie.db.source.remote.response.model.Genres

interface IResponse {
    val id: Int
    val backdropPath: String?
    val originalLanguage: String
    val overview: String
    val posterPath: String?
    val voteAverage: Float
    val genres: List<Genres>
}