package com.me.weather.presentation.di

import android.content.Context
import com.me.weather.data.datastore.DataStoreManager
import com.me.weather.data.datastore.UserPreferencesRepositoryImpl
import com.me.weather.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun providesDataStoreManager(context: Context): DataStoreManager {
        return DataStoreManager(context = context)
    }

    @Provides
    @Singleton
    fun providesUserPreferencesRepository(dataStoreManager: DataStoreManager): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStoreManager = dataStoreManager)
    }
}
