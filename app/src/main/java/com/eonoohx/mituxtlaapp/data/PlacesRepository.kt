package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.network.Place
import com.eonoohx.mituxtlaapp.network.PlacesApiService

interface PlacesRepository {
    suspend fun getPlacesData(placesSearch: String): List<Place>
}

class NetworkPlacesRepository(private val placesApiService: PlacesApiService) : PlacesRepository {
    override suspend fun getPlacesData(placesSearch: String): List<Place> =
        placesApiService.getPlaces(placesSearch)
}