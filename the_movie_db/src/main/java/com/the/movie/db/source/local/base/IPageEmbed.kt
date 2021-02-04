package com.the.movie.db.source.local.base

interface IPageEmbed<Result> {
    fun page(): Int
    fun totalPages(): Int
    fun totalResult(): Int
    fun results(): List<Result>
}