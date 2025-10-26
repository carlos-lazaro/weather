package com.me.weather.domain.use_case

import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.remote.api.sources.WeatherApiSource
import com.me.weather.domain.model.City
import com.me.weather.domain.model.ResponseResource
import com.me.weather.domain.model.Weather
import com.me.weather.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val weatherApiSource: WeatherApiSource,
    private val weatherRepository: WeatherRepository,
) {
    companion object {
        const val AGE_MINUTES_RECORD = 5
    }

    suspend operator fun invoke(
        query: City,
        pruneOldData: Boolean = false,
    ): ResponseResource<Weather> {
        weatherRepository
            .getWeatherByName(name = query)
            ?.takeIf {
                it.dt.isYoungerThanMinutes(AGE_MINUTES_RECORD)
            }?.let {
                Timber.d("Getting data from db, $it")
                return ResponseResource.Success(data = it)
            }

        return when (
            val res = weatherApiSource.loadData(query = query, pruneOldData = pruneOldData)
        ) {
            is ResponseResource.Error -> {
                ResponseResource.Error(res.errors, res.exception)
            }

            is ResponseResource.Success<WeatherEntity> -> {
                Timber.d("Getting data from api, ${res.data.toDomain()}")
                ResponseResource.Success(data = res.data.toDomain())
            }
        }
    }

    private fun Long.isYoungerThanMinutes(minutes: Int): Boolean {
        val nowUtcSeconds = System.currentTimeMillis() / 1000
        val diffSeconds = nowUtcSeconds - this
        return diffSeconds < minutes * 60
    }
}
