package com.me.weather.domain.repository

import com.me.weather.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAll(): Flow<List<Favorite>>

    suspend fun add(favorite: Favorite)

    suspend fun delete(favorite: Favorite)
}
