package com.android.developer.expert.core.domain.model.base

interface IModel<T> {
    companion object {
        const val DATA_NOT_VALID = -0x0
    }

    val id: T?

    fun isValid() = when (id) {
        is String -> (id as String).isNotEmpty()
        is Number -> (id as Number).toInt() > DATA_NOT_VALID
        else -> id != null
    }
}