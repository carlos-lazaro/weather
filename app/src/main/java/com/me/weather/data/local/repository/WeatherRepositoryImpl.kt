package com.me.weather.data.local.repository

import com.me.weather.data.local.dao.WeatherDao
import com.me.weather.domain.model.Weather
import com.me.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
) : WeatherRepository {
    override fun observeWeatherById(id: Int): Flow<Weather?> {
        return weatherDao.observeById(id)
    }
}
