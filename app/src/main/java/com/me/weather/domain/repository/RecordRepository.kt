package com.me.weather.domain.repository

import com.me.weather.domain.model.Record
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun observeAll(): Flow<List<Record>>

    suspend fun add(record: Record): EmptyResult<DataError.Local>

    suspend fun delete(record: Record)

    suspend fun deleteAll()
}
