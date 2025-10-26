package com.me.weather.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.me.weather.data.local.entities.WeatherEntity

data class WeatherResponseDto(
    @SerializedName("coord")
    val coord: CoordDto,
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: MainDto,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: WindDto,
    @SerializedName("clouds")
    val clouds: CloudsDto,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sys")
    val sys: SysDto,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Int,
)

fun WeatherResponseDto.toEntity(): WeatherEntity = WeatherEntity(
    id = id,
    main = weather.firstOrNull()?.main.orEmpty(),
    description = weather.firstOrNull()?.description.orEmpty(),
    icon = weather.firstOrNull()?.icon.orEmpty(),

    name = name,
    country = sys.country,
    lat = coord.lat,
    lon = coord.lon,

    temp = main.temp,
    feelsLike = main.feelsLike,
    tempMin = main.tempMin,
    tempMax = main.tempMax,
    pressure = main.pressure,
    humidity = main.humidity,
    wind = wind.speed,
    visibility = visibility,
    dt = dt,
    sunrise = sys.sunrise,
    sunset = sys.sunset,
)
