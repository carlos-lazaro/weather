package com.me.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.weather.domain.model.Weather

@Entity(tableName = "weathers")
data class WeatherEntity(
    @PrimaryKey
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

    val createdAt: Long = System.currentTimeMillis(),
)

fun Weather.toEntity(): WeatherEntity = WeatherEntity(
    id = id,
    main = main,
    description = description,
    icon = icon,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
    temp = temp,
    feelsLike = feelsLike,
    tempMin = tempMin,
    tempMax = tempMax,
    pressure = pressure,
    humidity = humidity,
    wind = wind,
    visibility = visibility,
    dt = dt,
    sunrise = sunrise,
    sunset = sunset,
)

fun WeatherEntity.toDomain(): Weather = Weather(
    id = id,
    main = main,
    description = description,
    icon = icon,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
    temp = temp,
    feelsLike = feelsLike,
    tempMin = tempMin,
    tempMax = tempMax,
    pressure = pressure,
    humidity = humidity,
    wind = wind,
    visibility = visibility,
    dt = dt,
    sunrise = sunrise,
    sunset = sunset,
)
