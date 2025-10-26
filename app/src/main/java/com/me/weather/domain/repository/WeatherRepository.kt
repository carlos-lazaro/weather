package com.me.weather.domain.repository

import com.me.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun observeWeatherById(id: Int): Flow<Weather?>
}
