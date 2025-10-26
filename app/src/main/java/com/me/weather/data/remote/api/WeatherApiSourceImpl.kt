package com.me.weather.data.remote.api

import androidx.room.withTransaction
import com.me.weather.data.local.Database
import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.dao.WeatherDao
import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.data.remote.api.sources.WeatherApiSource
import com.me.weather.data.remote.dto.toEntity
import com.me.weather.data.remote.service.WeatherService
import com.me.weather.domain.model.ResponseResource
import javax.inject.Inject

class WeatherApiSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val database: Database,
) : BaseApiCall(), WeatherApiSource {
    //    TODO: manage error
    override suspend fun loadData(
        query: String,
        pruneOldData: Boolean,
    ): ResponseResource<WeatherEntity> {
        return when (val weatherRes = safeApiCall { weatherService.getWeather(query) }) {
            is ResponseResource.Error -> ResponseResource.Error(
                errors = weatherRes.errors,
                exception = weatherRes.exception,
            )

            is ResponseResource.Success -> {
                when (val forecastRes = safeApiCall { weatherService.getForecast(query) }) {
                    is ResponseResource.Error -> ResponseResource.Error(
                        errors = forecastRes.errors,
                        exception = forecastRes.exception
                    )

                    is ResponseResource.Success -> {
                        val weatherEntity = weatherRes.data.toEntity()
                        val cityId = forecastRes.data.city.id
                        val cityIdInt = cityId.toInt()

                        val forecasts = listOf(
                            forecastRes.data.list[3].toEntity(cityIdInt),
                            forecastRes.data.list[11].toEntity(cityIdInt),
                            forecastRes.data.list[19].toEntity(cityIdInt),
                            forecastRes.data.list[27].toEntity(cityIdInt),
                            forecastRes.data.list[35].toEntity(cityIdInt),
                        )

                        try {
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

                            ResponseResource.Success(weatherEntity)
                        } catch (e: Exception) {
                            ResponseResource.Error(
                                exception = e,
                            )
                        }
                    }
                }
            }
        }
    }
}
