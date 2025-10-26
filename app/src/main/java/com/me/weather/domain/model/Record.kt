package com.me.weather.domain.model

data class Record(
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)
