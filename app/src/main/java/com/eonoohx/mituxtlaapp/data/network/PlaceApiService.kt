package com.eonoohx.mituxtlaapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApiService {
    @GET("place/search") // URL
    suspend fun getPlaces(@Query("query") search: String): List<Place>

    @GET("place/details")
    suspend fun getPlaceInfo(@Query("place_id") placeId: String, ): PlaceInfo
}