package com.me.weather.domain.repository

import com.me.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherById(id: Int): Flow<Weather?>
}
