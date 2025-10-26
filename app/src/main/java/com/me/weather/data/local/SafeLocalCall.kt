package com.me.weather.data.local

import android.database.sqlite.SQLiteFullException
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.Result
import kotlinx.coroutines.CancellationException
import timber.log.Timber

suspend fun <T> safeLocalCall(
    call: suspend () -> T,
): Result<T, DataError.Local> {
    return try {
        val result = call.invoke()
        Timber.d("Local DB Success: completed.")
        Result.Success(result)
    } catch (e: CancellationException) {
        throw e
    } catch (e: SQLiteFullException) {
        Timber.e(e, "Local DB Error: Disk is full.")
        Result.Error(DataError.Local.DiskFull)
    } catch (e: Exception) {
        Timber.e(e, "Local DB Error: Database corrupted or unexpected exception.")
        Result.Error(DataError.Local.DatabaseCorrupted)
    }
}
