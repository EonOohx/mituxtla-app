package com.eonoohx.mituxtlaapp

import com.eonoohx.mituxtlaapp.data.NetworkPlacesRepository
import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakePlacesApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkPlacesRepositoryTest {
    @Test
    fun networkPlacesRepository_getPlaces_veryPlaceList() = runTest {

        val repository = NetworkPlacesRepository(
            placesApiService = FakePlacesApiService()
        )
        assertEquals(FakeDataSource.fakePlaces, repository.getPlacesData(""))
    }

    @Test
    fun networkPlacesRepository_getPlaceInfo_veryPlaceDetails() = runTest {
        val repository = NetworkPlacesRepository(
            placesApiService = FakePlacesApiService()
        )
        assertEquals(FakeDataSource.fakePlaceInfo, repository.getPlaceInfoData(""))
    }
}