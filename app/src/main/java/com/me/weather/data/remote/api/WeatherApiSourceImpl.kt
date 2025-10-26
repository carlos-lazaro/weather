package com.me.weather.data.remote.api

import androidx.room.withTransaction
import com.me.weather.data.local.Database
import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.dao.WeatherDao
import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.data.local.safeLocalCall
import com.me.weather.data.remote.api.sources.WeatherApiSource
import com.me.weather.data.remote.dto.toEntity
import com.me.weather.data.remote.service.WeatherService
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.Result
import javax.inject.Inject

class WeatherApiSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val database: Database,
) : WeatherApiSource {
    override suspend fun loadData(
        query: String,
        pruneOldData: Boolean,
    ): Result<WeatherEntity, DataError> {
        return when (val weatherResult = safeApiCall { weatherService.getWeather(query) }) {
            is Result.Error -> Result.Error(weatherResult.error)
            is Result.Success -> {
                when (val forecastResult = safeApiCall { weatherService.getForecast(query) }) {
                    is Result.Error -> Result.Error(forecastResult.error)
                    is Result.Success -> {
                        val weatherEntity = weatherResult.data.toEntity()
                        val cityId = forecastResult.data.city.id
                        val cityIdInt = cityId.toInt()

                        val forecasts = listOf(
                            forecastResult.data.list[3].toEntity(cityIdInt),
                            forecastResult.data.list[11].toEntity(cityIdInt),
                            forecastResult.data.list[19].toEntity(cityIdInt),
                            forecastResult.data.list[27].toEntity(cityIdInt),
                            forecastResult.data.list[35].toEntity(cityIdInt),
                        )

                        val localResult = safeLocalCall {
                            database.withTransaction {
                                if (pruneOldData) {
                                    weatherDao.deleteAll()
                                    forecastDao.deleteAll()
                                } else {
                                    forecastDao.deleteByCityId(cityId)
                                }
                                weatherDao.insert(weatherEntity)
                                forecastDao.insert(forecasts)
                            }
                        }

                        return when (localResult) {
                            is Result.Success -> Result.Success(weatherEntity)
                            is Result.Error -> Result.Error(localResult.error)
                        }
                    }
                }
            }
        }
    }
}
