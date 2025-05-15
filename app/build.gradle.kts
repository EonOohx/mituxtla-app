plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.eonoohx.mituxtlaapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.eonoohx.mituxtlaapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URI", "\"https://mituxtla-api-production.up.railway.app/\"")
        }
        debug {
            buildConfigField("String", "BASE_URI", "\"https://mituxtla-api-production.up.railway.app/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // ViewModel Compose Library
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation Dependency
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.navigation.compose)


    // Networking - Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.androidx.junit.ktx)


    // Navigation Testing
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.espresso.intents)

    // Coroutine Testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Image Loading
    implementation(libs.coil3.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation(libs.androidx.room.ktx)

    //Preference Settings
    implementation("androidx.datastore:datastore-preferences:1.1.4")


    // Testing
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}