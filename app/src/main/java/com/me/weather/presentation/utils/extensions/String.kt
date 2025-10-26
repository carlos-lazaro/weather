package com.me.weather.presentation.utils.extensions

fun String.toUrlImgOpenWeather4x(): String {
    return "https://openweathermap.org/img/wn/${this}@4x.png"
}
