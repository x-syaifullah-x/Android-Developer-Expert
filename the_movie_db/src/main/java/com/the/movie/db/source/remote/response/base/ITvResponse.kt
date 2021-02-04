package com.the.movie.db.source.remote.response.base

import com.the.movie.db.source.remote.response.model.Genres

interface ITvResponse : IResponse {
    override val backdropPath: String?
    override val genres: List<Genres>
    override val id: Int
    override val originalLanguage: String
    override val overview: String
    override val posterPath: String?
    override val voteAverage: Float

    val firstAirDate: String
    val lastAirDate: String
    val name: String
    val originalName: String
}