package com.kotlin.workmanagercomposehilt

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class PeriodicCustomWork(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        delay(10000L.milliseconds)
        return Result.success()
    }
}

