package com.the.movie.db.source.local.base

interface ITvResultEntity : IResultEntity {
    val foreignKey: Int
    val originalName: String
    val name: String
}