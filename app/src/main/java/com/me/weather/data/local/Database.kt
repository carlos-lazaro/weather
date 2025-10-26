package com.me.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.weather.data.local.dao.FavoriteDao
import com.me.weather.data.local.dao.ForecastDao
import com.me.weather.data.local.dao.RecordDao
import com.me.weather.data.local.dao.WeatherDao
import com.me.weather.data.local.entities.FavoriteEntity
import com.me.weather.data.local.entities.ForecastEntity
import com.me.weather.data.local.entities.RecordEntity
import com.me.weather.data.local.entities.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class,
        RecordEntity::class,
        FavoriteEntity::class,
        ForecastEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun recordDao(): RecordDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun forecastDao(): ForecastDao
}
