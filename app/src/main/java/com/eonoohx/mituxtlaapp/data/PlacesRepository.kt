package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.network.PlaceApiService

interface PlacesRepository {
    suspend fun getPlacesData(search: String): List<Place>
    suspend fun getPlaceInfoData(placeId: String): PlaceInfo
}

class NetworkPlacesRepository(private val placeApiService: PlaceApiService) : PlacesRepository {
    override suspend fun getPlacesData(search: String): List<Place> =
        placeApiService.getPlaces(search)

    override suspend fun getPlaceInfoData(placeId: String): PlaceInfo =
        placeApiService.getPlaceInfo(placeId)
}