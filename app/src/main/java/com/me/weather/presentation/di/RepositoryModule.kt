package com.me.weather.presentation.di

import com.me.weather.data.local.dao.FavoriteDao
import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.dao.RecordDao
import com.me.weather.data.local.dao.WeatherDao
import com.me.weather.data.local.repository.FavoriteRepositoryImpl
import com.me.weather.data.local.repository.ForecastRepositoryImpl
import com.me.weather.data.local.repository.RecordRepositoryImpl
import com.me.weather.data.local.repository.WeatherRepositoryImpl
import com.me.weather.domain.repository.FavoriteRepository
import com.me.weather.domain.repository.ForecastRepository
import com.me.weather.domain.repository.RecordRepository
import com.me.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesUserPreferencesRepository(weatherDao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(weatherDao = weatherDao)
    }

    @Provides
    @Singleton
    fun providesForecastRepository(forecastDao: ForecastDao): ForecastRepository {
        return ForecastRepositoryImpl(forecastDao = forecastDao)
    }

    @Provides
    @Singleton
    fun providesFavoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao = favoriteDao)
    }

    @Provides
    @Singleton
    fun providesRecordRepository(recordDao: RecordDao): RecordRepository {
        return RecordRepositoryImpl(recordDao = recordDao)
    }
}
