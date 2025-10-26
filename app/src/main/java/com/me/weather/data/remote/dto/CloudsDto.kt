package com.me.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all")
    val all: Int,
)
