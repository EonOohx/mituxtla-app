package com.eonoohx.mituxtlaapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {
    @GET("place/search") // URL
    suspend fun getPlaces(@Query("query") placeSearch: String): List<Place>
}