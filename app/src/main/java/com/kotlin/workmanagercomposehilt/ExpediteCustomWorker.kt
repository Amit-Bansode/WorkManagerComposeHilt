package com.kotlin.workmanagercomposehilt

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@HiltWorker
class ExpediteCustomWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        // To show the notification, you must call setForeground
        setForeground(getForegroundInfo())
        
        delay(10000L.milliseconds)

        Log.d("TAG", "Success.. ")
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = createNotification(applicationContext)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(1, notification)
        }
    }
}

private fun createNotification(context: Context): Notification {
    val channelId = "channel_id"
    val channelName = "channel_name"

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Expedited WorkManager")
        .setContentText("Expedited WorkManager is running")
        .setAutoCancel(true)
        .setOngoing(true)
        .build()

    return builder
}
