package com.me.weather.domain.util

sealed interface DataError : Error {

    sealed interface Network : DataError {
        data object BadRequest : Network
        data object RequestTimeout : Network
        data object Unauthorized : Network
        data object Forbidden : Network
        data object NotFound : Network
        data object TooManyRequests : Network
        data object NoInternet : Network

        data object Serialization : Network
        data object Unknown : Network
        data class ServerError(val code: Int) : Network
    }

    sealed interface Local : DataError {
        data object DiskFull : Local
        data object DatabaseCorrupted : Local
    }
}
