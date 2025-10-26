package com.me.weather.data.remote.api.sources

import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.Result

interface WeatherApiSource {
    suspend fun loadData(
        query: String,
        pruneOldData: Boolean,
    ): Result<WeatherEntity, DataError>
}
