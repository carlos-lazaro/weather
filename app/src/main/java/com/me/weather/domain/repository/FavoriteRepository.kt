package com.me.weather.domain.repository

import com.me.weather.domain.model.Favorite
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeAll(): Flow<List<Favorite>>

    suspend fun add(favorite: Favorite): EmptyResult<DataError.Local>

    suspend fun delete(favorite: Favorite)
}
