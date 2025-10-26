package com.me.weather.domain.repository

import com.me.weather.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getAll(): Flow<List<Record>>

    suspend fun add(record: Record)

    suspend fun delete(record: Record)
}
