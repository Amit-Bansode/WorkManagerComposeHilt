package com.kotlin.workmanagercomposehilt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kotlin.workmanagercomposehilt.ui.theme.WorkManagerComposeHiltTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.time.delay
import java.time.Duration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //** use for normal one time work request
//        val workManager = OneTimeWorkRequestBuilder<CustomWorker>()
//            .setInitialDelay(Duration.ofSeconds(10))
//            .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10))
//            .build()
//
//        WorkManager.getInstance(this).enqueue(workManager)

        enableEdgeToEdge()
        setContent {
//            WorkManagerComposeHiltTheme {
//                val launcher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.RequestPermission(),
//                    onResult = { isGranted ->
//                        // Handle result if needed
//                    }
//                )
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    SideEffect {
//                        launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                    }
//                }
//
//                LaunchedEffect(Unit) {
//                    val workRequest = OneTimeWorkRequestBuilder<ExpediteCustomWorker>()
//                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
//                        .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10))
//                        .build()
//
//                    WorkManager.getInstance(this@MainActivity)
//                        .enqueue(workRequest)
//                }
//            }

            //Periodic work Request
            WorkManagerComposeHiltTheme {
                LaunchedEffect(Unit) {
                    val workRequest = PeriodicWorkRequestBuilder<PeriodicCustomWork>(
                        repeatInterval = 1,
                        repeatIntervalTimeUnit = java.util.concurrent.TimeUnit.HOURS,
                        flexTimeInterval = 15,
                        flexTimeIntervalUnit = java.util.concurrent.TimeUnit.MINUTES
                    ).setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10)).build()

                    WorkManager.getInstance(this@MainActivity).enqueueUniquePeriodicWork(
                        "PeriodicCustomWork",
                        ExistingPeriodicWorkPolicy.KEEP,
                        workRequest
                    )

                    delay(Duration.ofSeconds(10))
                    WorkManager.getInstance(this@MainActivity).cancelUniqueWork("PeriodicCustomWork")
                }

                WorkManager.getInstance(this@MainActivity).getWorkInfosForUniqueWorkLiveData(
                    "PeriodicCustomWork"
                ).observe(LocalLifecycleOwner.current) { workInfos ->
                    workInfos.forEach {
                        println("periodic work info: " + it.state)
                    }
                }
            }
        }
    }
}