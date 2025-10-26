package com.me.weather.presentation.utils.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.me.weather.domain.repository.UserPreferencesRepository
import com.me.weather.domain.repository.WeatherRepository
import com.me.weather.domain.use_case.LoadDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val weatherRepository: WeatherRepository,
    private val loadDataUseCase: LoadDataUseCase,
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val WORK_NAME = "WeatherUpdateWorker"
        const val WORK_INTERVAL = 15L
        const val WORK_INTERVAL_FIRST_RUN = 1L

        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val workRequest = PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
                WORK_INTERVAL,
                TimeUnit.MINUTES,
            )
                .setInitialDelay(WORK_INTERVAL_FIRST_RUN, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest,
                )
        }
    }

    override suspend fun doWork(): Result {
        return try {
            userPreferencesRepository
                .observeWeatherId()
                .first()
                ?.let {
                    weatherRepository
                        .getWeatherById(it)
                }
                ?.let {
                    loadDataUseCase(query = it.name, pruneOldData = true)
                }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
