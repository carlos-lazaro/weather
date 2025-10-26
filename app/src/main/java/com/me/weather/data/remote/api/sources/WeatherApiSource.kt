package com.me.weather.data.remote.api.sources

import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.domain.model.ResponseResource

interface WeatherApiSource {
    suspend fun loadData(
        query: String,
    ): ResponseResource<WeatherEntity>
}
