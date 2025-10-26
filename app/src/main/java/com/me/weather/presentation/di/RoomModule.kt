package com.me.weather.presentation.di

import android.app.Application
import androidx.room.Room
import com.me.weather.data.local.Database
import com.me.weather.data.local.dao.FavoriteDao
import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.dao.RecordDao
import com.me.weather.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
    ): Database {
        return Room
            .databaseBuilder(
                application,
                Database::class.java,
                "database.db",
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCartPokemonDao(appDatabase: Database): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideRecordDao(appDatabase: Database): RecordDao {
        return appDatabase.recordDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: Database): FavoriteDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideForecastDao(appDatabase: Database): ForecastDao {
        return appDatabase.forecastDao()
    }
}
