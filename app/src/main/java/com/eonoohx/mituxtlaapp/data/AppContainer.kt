package com.eonoohx.mituxtlaapp.data

import android.content.Context
import com.eonoohx.mituxtlaapp.BuildConfig
import com.eonoohx.mituxtlaapp.data.database.FavoritePlacesDatabase
import com.eonoohx.mituxtlaapp.data.network.PlaceApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val placesRepository: PlacesRepository
    val databaseRepository: DatabaseRepository
}

class PlacesApplication(private val context: Context) : AppContainer {
    private val baseUri: String = BuildConfig.BASE_URI
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUri)
        .build()

    private val retrofitService: PlaceApiService by lazy {
        retrofit.create(PlaceApiService::class.java)
    }

    override val placesRepository: PlacesRepository by lazy {
        NetworkPlacesRepository(retrofitService)
    }

    override val databaseRepository: DatabaseRepository by lazy {
        UserPlacesRepository(FavoritePlacesDatabase.getDatabase(context).placeDao())
    }
}