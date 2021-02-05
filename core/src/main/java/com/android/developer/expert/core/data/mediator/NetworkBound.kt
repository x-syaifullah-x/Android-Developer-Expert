package com.android.developer.expert.core.data.mediator

import com.android.developer.expert.core.domain.model.Resource
import com.android.developer.expert.core.domain.model.base.IModel
import com.the.movie.db.source.remote.network.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBound(
    crossinline loadFromDB: () -> Flow<ResultType>,
    crossinline onFetchError: (Throwable) -> Unit = { },
    crossinline onFetchEmpty: (ResultType?) -> Unit = { },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline fetch: suspend () -> Flow<ApiResponse<RequestType>>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline isValid: (ResultType?) -> Boolean = {
        when (it) {
            is IModel<*> -> it.isValid()
            is List<*> -> it.isNotEmpty()
            else -> if (it == null) false else throw IllegalArgumentException("resultType not support")
        }
    },
) = flow {

    val resourceSuccessOrEmpty = { type: ResultType ->
        if (isValid(type)) Resource.Success(type) else Resource.Empty
    }

    val resultType = loadFromDB().firstOrNull()
    val firstResource = resultType
        ?.let { (if (isValid(it)) Resource.Success(it) else Resource.Loading) }
        ?: Resource.Loading
    emit(firstResource)

    val result =
        if (shouldFetch(resultType)) {
            when (val apiResponse = fetch().first()) {
                is ApiResponse.Success -> saveFetchResult(apiResponse.data).run {
                    loadFromDB().map { Resource.Success(it) }
                }
                is ApiResponse.Empty -> onFetchEmpty(resultType).run {
                    loadFromDB().map { resourceSuccessOrEmpty(it) }
                }
                is ApiResponse.Error -> onFetchError(apiResponse.throwable).run {
                    loadFromDB().map {
                        Resource.Error(apiResponse.throwable, if (isValid(it)) it else null)
                    }
                }
            }
        } else {
            loadFromDB().map { resourceSuccessOrEmpty(it) }
        }
    emitAll(result)
}.flowOn(Dispatchers.IO)