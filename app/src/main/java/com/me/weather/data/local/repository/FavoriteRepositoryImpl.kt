package com.me.weather.data.local.repository

import com.me.weather.data.local.dao.FavoriteDao
import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.local.entities.toEntity
import com.me.weather.data.local.safeLocalCall
import com.me.weather.domain.model.Favorite
import com.me.weather.domain.repository.FavoriteRepository
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override fun observeAll(): Flow<List<Favorite>> {
        return favoriteDao
            .streamAll()
            .map { it.toDomain() }
    }

    override suspend fun add(favorite: Favorite): EmptyResult<DataError.Local> {
        return safeLocalCall {
            favoriteDao.insert(favorite.toEntity())
        }
    }

    override suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite.toEntity())
    }
}
