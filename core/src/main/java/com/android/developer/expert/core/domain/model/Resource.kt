package com.android.developer.expert.core.domain.model

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error<T>(val throwable: Throwable, val data: T? = null) : Resource<T>()
}