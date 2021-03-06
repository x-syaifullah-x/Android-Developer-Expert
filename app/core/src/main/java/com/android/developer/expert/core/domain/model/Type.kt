package com.android.developer.expert.core.domain.model

import android.content.Intent
import android.os.Parcelable
import com.android.developer.expert.core.domain.model.Type.Companion.DATA_EXTRA
import kotlinx.parcelize.Parcelize

sealed class Type<out Int> : Parcelable {

    companion object {
        const val DATA_EXTRA = "DATA_EXTRA"
    }

    @Parcelize
    data class Movie(val id: Int) : Type<Int>()

    @Parcelize
    data class Tv(val id: Int) : Type<Int>()
}

fun Intent.putType(type: Type<*>): Intent = putExtra(
    DATA_EXTRA,
    when (type) {
        is Type.Movie -> Type.Movie(type.id)
        is Type.Tv -> Type.Tv(type.id)
    }
)

fun Intent.getTheMovieType() =
    getParcelableExtra<Type<Int>>(DATA_EXTRA) ?: run {
        val message = "getTheMovieType() require data type ${Type::class.java.name}"
        throw NullPointerException(message)
    }