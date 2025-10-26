package com.me.weather.data.remote.api

import com.me.weather.domain.model.ResponseResource
import retrofit2.Response
import java.io.IOException

open class BaseApiCall {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ResponseResource<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            return ResponseResource.Error(exception = e)
        }

//        TODO: improve
        if (!response.isSuccessful) {
            return when (response.code()) {
                in 400..499 -> ResponseResource.Error(exception = IOException("Error del cliente (${response.code()}): Verifica los datos enviados."))

                in 500..599 -> ResponseResource.Error(exception = IOException("Error del servidor (${response.code()}): Intenta más tarde."))

                else -> {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        ResponseResource.Error(exception = IOException("${response.code()}: Error inesperado."))
                    } else
                        ResponseResource.Error(exception = IOException("${response.code()}: Gracias por tu paciencia mientras lo resolvemos."))
                }
            }
        }

        val responseBody = response.body()
            ?: return ResponseResource.Error(exception = IOException("Error, ocurrio algo inesperado."))

        return ResponseResource.Success(responseBody)
    }
}
