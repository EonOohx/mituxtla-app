package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.PlacesRepository
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo

class FakeNetworkPlaceRepository : PlacesRepository {
    override suspend fun getPlacesData(search: String): List<Place> = FakeDataSource.fakePlaces

    override suspend fun getPlaceInfoData(placeId: String): PlaceInfo = FakeDataSource.fakePlaceInfo
}