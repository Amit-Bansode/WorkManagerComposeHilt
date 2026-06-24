# WorkManager Compose Hilt

A sample Android application demonstrating the integration of **Jetpack WorkManager**, **Jetpack Compose**, and **Hilt** for dependency injection.

## Tech Stack
- **Jetpack Compose**: Modern UI toolkit.
- **Hilt**: Dependency Injection (including Worker injection).
- **WorkManager**: Background task scheduling (One-time, Periodic, and Expedited).
- **Retrofit & OkHttp**: For network communication.
- **KSP**: Kotlin Symbol Processing for faster builds.

## Features
- **Hilt-Injected Worker**: A `CustomWorker` that receives a `DemoService` via Hilt.
- **Expedited Work**: `ExpediteCustomWorker` demonstrates how to run immediate tasks with foreground notifications.
- **Periodic Work**: Shows how to schedule repeating tasks with unique names.
- **Custom Configuration**: Manual WorkManager initialization in `MainApplication` using `HiltWorkerFactory`.
- **Android 14+ Support**: Includes required Foreground Service types and runtime notification permissions.

## Key Implementation Details

### Worker Injection
The workers are annotated with `@HiltWorker` and use `@AssistedInject`. Hilt handles the injection of regular dependencies (like `DemoService`), while WorkManager provides the `@Assisted` parameters (`Context` and `WorkerParameters`).

### Application Class
`MainApplication` implements `Configuration.Provider`. Note the use of a property getter for `workManagerConfiguration` to ensure `workerFactory` is initialized by Hilt before being accessed:

```kotlin
@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
```

### Foreground Service & Android 14 (API 34+)
To support Android 14 and higher, the app:
1. Declares `FOREGROUND_SERVICE_DATA_SYNC` in `AndroidManifest.xml`.
2. Specifies the `foregroundServiceType` in the `SystemForegroundService` declaration.
3. Requests `POST_NOTIFICATIONS` permission at runtime.
4. Uses `ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC` in `getForegroundInfo()`.

### Suspending Tasks in Compose
Suspending functions like `delay` or WorkManager cancellation inside the UI are wrapped in `LaunchedEffect` to ensure they run in the correct coroutine scope.

## Getting Started
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app.
4. Grant the notification permission when prompted.
5. Observe the background task status in Logcat (tag "TAG") and the persistent notification for expedited work.
