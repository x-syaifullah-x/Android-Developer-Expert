package com.the.movie.db.source.local.base

interface IMovieResultEntity : IResultEntity {
    val foreignKey: Int
    val originalTitle: String?
    val title: String?
}