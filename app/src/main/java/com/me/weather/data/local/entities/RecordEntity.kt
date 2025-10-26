package com.me.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.weather.domain.model.Record

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)

fun RecordEntity.toDomain(): Record = Record(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)

fun List<RecordEntity>.toDomain(): List<Record> =
    map { it.toDomain() }

fun Record.toEntity(): RecordEntity = RecordEntity(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
)
