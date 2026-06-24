package com.kotlin.workmanagercomposehilt

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.UnknownHostException

@HiltWorker
class CustomWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val demoService: DemoService
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        try {
            val response = demoService.getPost(1)
            return if (response.isSuccessful) {
                Log.d("TAG", "response: " + response.body())
                Result.success()
            } else {
                Log.d("TAG", "response: " + response.errorBody())
                Log.d("TAG", "Retrying.. ")

                Result.retry()
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                Log.d("TAG", "Retrying.. ")

                return Result.retry()
            }
            Log.d("TAG", e.message.toString())
            return Result.failure(Data.Builder().putString("error", e.message).build())
        }
    }
}