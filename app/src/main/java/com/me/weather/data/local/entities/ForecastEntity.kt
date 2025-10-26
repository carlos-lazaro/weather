package com.me.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.weather.domain.model.Forecast

@Entity(tableName = "forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,

    val main: String,
    val description: String,
    val icon: String,
    val clouds: Int,

    val windSpeed: Double,
    val windDeg: Int,
    val visibility: Int,
    val pop: Double,
    val partOfDay: String,

    val dateText: String,
    val cityId: Int,
    val createdAt: Long = System.currentTimeMillis(),
)

fun ForecastEntity.toDomain(): Forecast {
    return Forecast(
        id = id,
        dt = dt,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        pressure = pressure,
        humidity = humidity,
        main = main,
        description = description,
        icon = icon,
        clouds = clouds,
        windSpeed = windSpeed,
        windDeg = windDeg,
        visibility = visibility,
        pop = pop,
        partOfDay = partOfDay,
        dateText = dateText,
        cityId = cityId,
        createdAt = createdAt,
    )
}

fun List<ForecastEntity>.toDomain(): List<Forecast> {
    return map { it.toDomain() }
}
