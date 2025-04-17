package com.eonoohx.mituxtlaapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.eonoohx.mituxtlaapp.data.AppContainer
import com.eonoohx.mituxtlaapp.data.PlacesApplication
import com.eonoohx.mituxtlaapp.data.preference.UserPreferencesRepository
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val THEME_PREFERENCE_NAME = "theme_preference"
private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = THEME_PREFERENCE_NAME)

class MiTuxtlaApplication : Application(), SingletonImageLoader.Factory {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        container = PlacesApplication(this) // "This" is required to instantiate the database.
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

    // Allows retrieving images from URLs using OkHttpClient
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader(this).newBuilder().components {
            add(
                OkHttpNetworkFetcherFactory(
                    callFactory = OkHttpClient.Builder().followRedirects(true)
                        .readTimeout(10000, TimeUnit.MILLISECONDS).build()
                )
            )
        }.build()
    }
}