package com.me.weather.data.local.repository

import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.entities.toDomain
import com.me.weather.domain.model.Forecast
import com.me.weather.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastDao: ForecastDao,
) : ForecastRepository {
    override fun observeForecastByCityId(id: Int): Flow<List<Forecast>> {
        return forecastDao
            .observeAllByCityId(id)
            .map { it.toDomain() }
    }
}
