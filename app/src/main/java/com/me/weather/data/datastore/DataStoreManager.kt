package com.me.weather.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val WEATHER_ID = intPreferencesKey("weather_id")
    }

    suspend fun setWeatherId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[WEATHER_ID] = id
        }
    }

    fun getWeatherId(): Flow<Int?> = context.dataStore.data.map { it[WEATHER_ID] }
}
