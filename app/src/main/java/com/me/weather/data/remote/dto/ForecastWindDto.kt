package com.me.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastWindDto(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double?,
)
