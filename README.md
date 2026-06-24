# WorkManager Compose Hilt

A sample Android application demonstrating the integration of **Jetpack WorkManager**, **Jetpack Compose**, and **Hilt** for dependency injection.

## Tech Stack
- **Jetpack Compose**: Modern UI toolkit.
- **Hilt**: Dependency Injection (including Worker injection).
- **WorkManager**: Background task scheduling.
- **Retrofit & OkHttp**: For network communication.
- **KSP**: Kotlin Symbol Processing for faster builds.

## Features
- **Hilt-Injected Worker**: A `CustomWorker` that receives a `DemoService` via Hilt.
- **Custom Configuration**: Manual WorkManager initialization in `MainApplication` using `HiltWorkerFactory`.
- **Background Networking**: Performs a network request to JSONPlaceholder in the background.

## Key Implementation Details

### Worker Injection
The `CustomWorker` is annotated with `@HiltWorker` and uses `@AssistedInject`. Hilt handles the injection of regular dependencies (`DemoService`), while WorkManager provides the `@Assisted` parameters (`Context` and `WorkerParameters`).

### Application Class
`MainApplication` implements `Configuration.Provider` to provide the `HiltWorkerFactory` to WorkManager:

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

### AndroidManifest.xml
The default `InitializationProvider` for WorkManager is removed to allow for the custom configuration in `MainApplication`:

```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    tools:node="remove" />
```

## Getting Started
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app.
4. Check the Logcat (tag "TAG") to see the background work execution after a 10-second delay.
