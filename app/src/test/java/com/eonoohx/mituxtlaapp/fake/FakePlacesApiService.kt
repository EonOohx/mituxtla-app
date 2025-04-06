package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.network.PlacesApiService
import com.eonoohx.mituxtlaapp.fake.FakeDataSource.fakePlaceInfo
import com.eonoohx.mituxtlaapp.fake.FakeDataSource.fakePlaces

class FakePlacesApiService : PlacesApiService {
    override suspend fun getPlaces(search: String): List<Place> = fakePlaces

    override suspend fun getPlaceInfo(placeId: String): PlaceInfo = fakePlaceInfo
}