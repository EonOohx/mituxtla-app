package com.eonoohx.mituxtlaapp

import android.app.Application
import com.eonoohx.mituxtlaapp.data.AppContainer
import com.eonoohx.mituxtlaapp.data.PlacesApplication

class MiTuxtlaApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PlacesApplication()
    }
}