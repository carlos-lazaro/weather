package com.me.weather.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveWeatherId(id: Int)
    fun observeWeatherId(): Flow<Int?>
}
