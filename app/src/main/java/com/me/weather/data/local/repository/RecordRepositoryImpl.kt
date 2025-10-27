package com.me.weather.data.local.repository

import com.me.weather.data.local.dao.RecordDao
import com.me.weather.data.local.entities.toDomain
import com.me.weather.data.local.entities.toEntity
import com.me.weather.data.local.safeLocalCall
import com.me.weather.domain.model.Record
import com.me.weather.domain.repository.RecordRepository
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDao: RecordDao,
) : RecordRepository {
    override fun observeAll(): Flow<List<Record>> {
        return recordDao
            .observeAll()
            .map { it.toDomain() }
    }

    override suspend fun add(record: Record): EmptyResult<DataError.Local> {
        return safeLocalCall {
            recordDao.insert(record.toEntity())
        }
    }

    override suspend fun delete(record: Record) {
        recordDao.delete(record.toEntity())
    }

    override suspend fun deleteAll() {
        recordDao.deleteAll()
    }
}
