package com.the.movie.db.source.remote.network.utils

sealed class ApiResponse<out R> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Error(val throwable: Throwable) : ApiResponse<Nothing>()

    object Empty : ApiResponse<Nothing>()

    companion object {
        suspend fun <T> fetch(
            block: suspend () -> ApiResponse<T>,
        ) = try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e)
        }
    }
}