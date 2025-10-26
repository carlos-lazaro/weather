package com.me.weather.domain.model

data class Forecast(
    val id: Int = 0,

    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,

    val main: String,
    val description: String,
    val icon: String,
    val clouds: Int,

    val windSpeed: Double,
    val windDeg: Int,
    val visibility: Int,
    val pop: Double,
    val partOfDay: String,

    val dateText: String,
    val cityId: Int,
    val createdAt: Long,
)
