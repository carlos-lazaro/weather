package com.me.weather.data.local.repository

import com.me.weather.data.local.dao.FavoriteDao
import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.local.entities.toEntity
import com.me.weather.domain.model.Favorite
import com.me.weather.domain.repository.FavoriteRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override fun getAll(): Flow<List<Favorite>> {
        return favoriteDao
            .streamAll()
            .map { it.toDomain() }
    }

    override suspend fun add(favorite: Favorite) {
        favoriteDao.insert(favorite.toEntity())
    }

    override suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite.toEntity())
    }
}
