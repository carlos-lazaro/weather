package com.me.weather.presentation.utils

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()

    data object Loading : RequestState<Nothing>()

    data class Success<out T>(val data: T) : RequestState<T>()

    data class Error(
        val message: String? = null,
        val errors: HashMap<String, String>? = null,
        val exception: Exception? = null,
        val code: Int? = null,
    ) : RequestState<Nothing>()
}
