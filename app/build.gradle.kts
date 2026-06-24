plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.kotlin.workmanagercomposehilt"
    compileSdk =37

    defaultConfig {
        applicationId = "com.kotlin.workmanagercomposehilt"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)

    // Hilt Core
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler) // Use kapt("...") if using Kapt


    //coroutines
    // Core coroutines functionality (structures, builders like launch/async)
    implementation(libs.kotlinx.coroutines.core)

    // Android support (provides Dispatchers.Main and UI thread integration)
    implementation(libs.kotlinx.coroutines.android)

    //retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)

    // 2. Add this line to fix the "not found" error
    implementation(libs.logging.interceptor)

//    // Hilt Navigation Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp("org.jetbrains.kotlin:kotlin-metadata-jvm:${libs.versions.kotlin.get()}")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}