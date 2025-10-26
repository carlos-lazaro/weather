package com.me.weather.domain.use_case

import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.remote.api.sources.WeatherApiSource
import com.me.weather.domain.model.City
import com.me.weather.domain.model.ResponseResource
import com.me.weather.domain.model.Weather
import jakarta.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val weatherApiSource: WeatherApiSource,
) {
    suspend operator fun invoke(query: City): ResponseResource<Weather> {
        return when (val res = weatherApiSource.loadData(query = query)) {
            is ResponseResource.Error -> {
                ResponseResource.Error(res.errors, res.exception)
            }

            is ResponseResource.Success<WeatherEntity> -> {
                ResponseResource.Success(data = res.data.toDomain())
            }
        }
    }
}
