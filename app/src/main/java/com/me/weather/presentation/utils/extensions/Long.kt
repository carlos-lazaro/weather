package com.me.weather.presentation.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private fun format(value: Long, format: String, locale: Locale): String {
    val date = Date(value * 1000)
    val formatter = SimpleDateFormat(format, locale)
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(date)
}

fun Long.toPrettyDate(
    format: String = "EEE, dd MMM yyyy HH:mm",
    locale: Locale = Locale.getDefault(),
): String {
    return format(this, format, locale)
}

fun Long.toPrettyDateShort(
    format: String = "EEE, MMM dd",
    locale: Locale = Locale.getDefault(),
): String {
    return format(this, format, locale)
}

fun Long.toPrettyDateSunsetSunrise(
    format: String = "h:mm a",
    locale: Locale = Locale.getDefault(),
): String {
    return format(this, format, locale)
}
