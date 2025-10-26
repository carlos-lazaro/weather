package com.me.weather.domain.util

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.me.weather.domain.util.Error>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <D, E : Error, R : Error> Result<D, E>.mapError(map: (E) -> R): Result<D, R> {
    return when (this) {
        is Result.Success -> Result.Success(data)
        is Result.Error -> Result.Error(map(error))
    }
}

inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

inline fun <D, E : Error> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    if (this is Result.Error) {
        action(error)
    }
    return this
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

typealias EmptyResult<E> = Result<Unit, E>
