package com.kotlin.workmanagercomposehilt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.kotlin.workmanagercomposehilt.ui.theme.WorkManagerComposeHiltTheme
import dagger.hilt.android.AndroidEntryPoint
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
            WorkManagerComposeHiltTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        // Handle result if needed
                    }
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    SideEffect {
                        launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                LaunchedEffect(Unit) {
                    val workRequest = OneTimeWorkRequestBuilder<ExpediteCustomWorker>()
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10))
                        .build()

                    WorkManager.getInstance(this@MainActivity)
                        .enqueue(workRequest)
                }
            }
        }
    }
}