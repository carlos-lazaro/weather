package com.me.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.weather.domain.model.Favorite

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)

fun FavoriteEntity.toDomain(): Favorite = Favorite(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)

fun List<FavoriteEntity>.toDomain(): List<Favorite> =
    map { it.toDomain() }

fun Favorite.toEntity(): FavoriteEntity = FavoriteEntity(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)
