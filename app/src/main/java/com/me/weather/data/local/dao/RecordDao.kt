package com.me.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.me.weather.data.local.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recordEntity: RecordEntity)

    @Update
    suspend fun update(recordEntity: RecordEntity)

    @Delete
    suspend fun delete(recordEntity: RecordEntity)

    @Query("DELETE FROM records WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM records")
    fun observeAll(): Flow<List<RecordEntity>>
}
