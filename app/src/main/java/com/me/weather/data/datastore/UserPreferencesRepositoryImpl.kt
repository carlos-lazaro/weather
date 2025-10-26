package com.me.weather.data.datastore

import com.me.weather.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : UserPreferencesRepository {
    override suspend fun saveWeatherId(id: Int) {
        dataStoreManager.setWeatherId(id)
    }

    override fun observeWeatherId(): Flow<Int?> {
        return dataStoreManager.getWeatherId()
    }
}
