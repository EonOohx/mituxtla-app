package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.network.PlacesApiService

interface PlacesRepository {
    suspend fun getPlacesData(search: String): List<Place>
    suspend fun getPlaceInfoData(placeId: String): PlaceInfo
}

class NetworkPlacesRepository(private val placesApiService: PlacesApiService) : PlacesRepository {
    override suspend fun getPlacesData(search: String): List<Place> =
        placesApiService.getPlaces(search)

    override suspend fun getPlaceInfoData(placeId: String): PlaceInfo =
        placesApiService.getPlaceInfo(placeId)
}