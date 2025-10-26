package com.me.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.me.weather.data.local.entities.WeatherEntity
import com.me.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Update
    suspend fun update(weatherEntity: WeatherEntity)

    @Delete
    suspend fun delete(weatherEntity: WeatherEntity)

    @Query("DELETE FROM weathers WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM weathers WHERE id = :id")
    fun observeById(id: Int): Flow<Weather?>

    @Query("SELECT * FROM weathers")
    fun observeAll(): Flow<List<WeatherEntity>>
}
