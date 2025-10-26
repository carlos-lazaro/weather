package com.me.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.me.weather.data.local.entities.ForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastEntity: ForecastEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastEntities: List<ForecastEntity>)

    @Update
    suspend fun update(forecastEntity: ForecastEntity)

    @Delete
    suspend fun delete(forecastEntity: ForecastEntity)

    @Query("DELETE FROM forecasts WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM forecasts WHERE cityId = :id")
    suspend fun deleteByCityId(id: Long)

    @Query("SELECT * FROM forecasts WHERE id = :id")
    suspend fun getById(id: Int): ForecastEntity?

    @Query("SELECT * FROM forecasts WHERE cityId = :cityId")
    suspend fun getAllByCityId(cityId: Long): List<ForecastEntity>

    @Query("SELECT * FROM forecasts WHERE cityId = :cityId")
     fun observeAllByCityId(cityId: Int): Flow<List<ForecastEntity>>

    @Query("SELECT * FROM forecasts")
    fun observeAll(): Flow<List<ForecastEntity>>
}
