package com.me.weather.domain.model

sealed class ResponseResource<out R> {
    data class Success<out T>(val data: T) : ResponseResource<T>()
    data class Error(
        val errors: HashMap<String, String>? = null,
        val exception: Exception? = null,
    ) : ResponseResource<Nothing>()
}
