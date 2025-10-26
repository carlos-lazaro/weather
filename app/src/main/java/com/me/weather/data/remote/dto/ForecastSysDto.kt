package com.me.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastSysDto(
    @SerializedName("pod")
    val pod: String,
)
