package com.me.weather.domain.model

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,

    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,

    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val wind: Double,
    val visibility: Int,
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
)

fun Weather.toFavorite() = Favorite(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)

fun Weather.toRecord() = Record(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)
