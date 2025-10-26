package com.me.weather.domain.repository

import com.me.weather.domain.model.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    fun observeForecastByCityId(id: Int): Flow<List<Forecast>>
}
