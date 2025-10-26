package com.me.weather.domain.use_case

import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.remote.api.sources.WeatherApiSource
import com.me.weather.domain.model.City
import com.me.weather.domain.model.Weather
import com.me.weather.domain.repository.WeatherRepository
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.Result
import com.me.weather.domain.util.map
import com.me.weather.domain.util.onSuccess
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
    ): Result<Weather, DataError> {
        weatherRepository
            .getWeatherByName(name = query)
            ?.takeIf {
                it.dt.isYoungerThanMinutes(AGE_MINUTES_RECORD)
            }?.let {
                Timber.d("Getting data from db, $it")
                return Result.Success(it)
            }

        return weatherApiSource
            .loadData(query = query, pruneOldData = pruneOldData)
            .map {
                it.toDomain()
            }
            .onSuccess {
                Timber.d("Getting data from api, ${it}")
            }
    }

    private fun Long.isYoungerThanMinutes(minutes: Int): Boolean {
        val nowUtcSeconds = System.currentTimeMillis() / 1000
        val diffSeconds = nowUtcSeconds - this
        return diffSeconds < minutes * 60
    }
}
