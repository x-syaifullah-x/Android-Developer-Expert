package com.the.movie.db.source.remote.response

import com.google.gson.annotations.SerializedName
import com.the.movie.db.source.remote.response.base.IMovieResponse
import com.the.movie.db.source.remote.response.model.Genres

data class MovieResponse(
    @SerializedName("backdrop_path") override val backdropPath: String?,
    @SerializedName("genres") override val genres: List<Genres>,
    @SerializedName("id") override val id: Int,
    @SerializedName("original_language") override val originalLanguage: String,
    @SerializedName("overview") override val overview: String,
    @SerializedName("poster_path") override val posterPath: String?,
    @SerializedName("vote_average") override val voteAverage: Float,
    @SerializedName("original_title") override val originalTitle: String,
    @SerializedName("release_date") override val releaseDate: String?,
    @SerializedName("title") override val title: String,
) : IMovieResponse