package com.kotlin.workmanagercomposehilt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kotlin.workmanagercomposehilt.ui.theme.WorkManagerComposeHiltTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workManager = OneTimeWorkRequestBuilder<CustomWorker>()
            .setInitialDelay(Duration.ofSeconds(10))
            .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10))
            .build()

        WorkManager.getInstance(this).enqueue(workManager)

        enableEdgeToEdge()
        setContent {
            WorkManagerComposeHiltTheme {

            }
        }
    }
}