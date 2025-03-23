package com.eonoohx.mituxtlaapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.eonoohx.mituxtlaapp.data.AppContainer
import com.eonoohx.mituxtlaapp.data.PlacesApplication
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MiTuxtlaApplication : Application(), SingletonImageLoader.Factory {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PlacesApplication()
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader(this).newBuilder().components {
            add(
                OkHttpNetworkFetcherFactory(
                    callFactory = OkHttpClient.Builder().followRedirects(true)
                        .readTimeout(8000, TimeUnit.MILLISECONDS).build()
                )
            )
        }.build()
    }
}