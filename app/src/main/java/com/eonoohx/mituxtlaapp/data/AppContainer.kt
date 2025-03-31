package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.BuildConfig
import com.eonoohx.mituxtlaapp.network.PlacesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val placesRepository: PlacesRepository
}

class PlacesApplication : AppContainer {
    private val baseUri: String = BuildConfig.BASE_URI
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUri)
        .build()

    private val retrofitService: PlacesApiService by lazy {
        retrofit.create(PlacesApiService::class.java)
    }

    override val placesRepository: PlacesRepository by lazy {
        NetworkPlacesRepository(retrofitService)
    }
}