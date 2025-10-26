package com.me.weather.domain.repository

import com.me.weather.domain.model.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getForecastByCityId(id: Int): Flow<List<Forecast>>
}
