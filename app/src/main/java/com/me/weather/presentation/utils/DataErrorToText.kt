package com.me.weather.presentation.utils

import com.me.weather.R
import com.me.weather.domain.util.DataError

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DiskFull -> UiText.StringResource(
            R.string.error_disk_full,
        )

        DataError.Local.DatabaseCorrupted -> UiText.StringResource(
            R.string.error_db_corrupted,
        )

        DataError.Network.BadRequest -> UiText.StringResource(
            R.string.error_bad_request
        )

        DataError.Network.RequestTimeout -> UiText.StringResource(
            R.string.error_request_timeout
        )

        DataError.Network.Unauthorized -> UiText.StringResource(
            R.string.error_unauthorized
        )

        DataError.Network.Forbidden -> UiText.StringResource(
            R.string.error_forbidden
        )

        DataError.Network.NotFound -> UiText.StringResource(
            R.string.error_not_found
        )

        DataError.Network.TooManyRequests -> UiText.StringResource(
            R.string.error_too_many_requests
        )

        DataError.Network.NoInternet -> UiText.StringResource(
            R.string.error_no_internet
        )

        DataError.Network.Serialization -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.Unknown -> UiText.StringResource(
            R.string.error_unknown
        )

        is DataError.Network.ServerError -> UiText.StringResource(
            R.string.error_server_error
        )

        else -> UiText.StringResource(R.string.error_unknown)
    }
}