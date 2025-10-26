package com.me.weather.data.remote.api

import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.Result
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>,
): Result<T, DataError.Network> {
    return try {
        val response = call.invoke()

        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Network.Serialization)
            }
        }
        Timber.e("Network Error: ${response.code()}")

        return when (response.code()) {
            400 -> Result.Error(DataError.Network.BadRequest)
            401 -> Result.Error(DataError.Network.Unauthorized)
            403 -> Result.Error(DataError.Network.Forbidden)
            404 -> Result.Error(DataError.Network.NotFound)
            408 -> Result.Error(DataError.Network.RequestTimeout)
            429 -> Result.Error(DataError.Network.TooManyRequests)
            in 500..599 -> Result.Error(DataError.Network.ServerError(response.code()))
            else -> Result.Error(DataError.Network.Unknown)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: UnknownHostException) {
        Timber.e(e, "No Internet Connection")
        Result.Error(DataError.Network.NoInternet)
    } catch (e: SocketTimeoutException) {
        Timber.e(e, "Request Timeout")
        Result.Error(DataError.Network.RequestTimeout)
    } catch (e: IOException) {
        Timber.e(e, "Network IO Exception")
        Result.Error(DataError.Network.NoInternet)
    } catch (e: Exception) {
        Timber.e(e, "Unknown network error")
        Result.Error(DataError.Network.Unknown)
    }
}
