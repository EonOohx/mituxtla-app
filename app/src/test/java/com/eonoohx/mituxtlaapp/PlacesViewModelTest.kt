package com.eonoohx.mituxtlaapp

import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.model.PlacesServiceUiState
import com.eonoohx.mituxtlaapp.ui.model.PlacesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlacesViewModelTest {
    private lateinit var placesViewModel: PlacesViewModel

    @get: Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        placesViewModel = PlacesViewModel(
            placesRepository = FakeNetworkPlaceRepository(),
            databaseRepository = FakeFavoritePlaceDatabase()
        )
    }

    @Test
    fun placesViewModel_getPlaces_verifyListPlacesUiSateSuccess() = runTest {
        placesViewModel.getApiPlacesList("")
        assertEquals(
            PlacesServiceUiState.Success(FakeDataSource.fakePlaces),
            placesViewModel.listPlacesUiState
        )
    }

    @Test
    fun placesViewModel_getPlaceInfo_verifyPlaceInfoUiState() = runTest {
        placesViewModel.getApiPlaceInfo("")
        assertEquals(
            PlacesServiceUiState.Success(FakeDataSource.fakePlaceInfo),
            placesViewModel.placeInfoUiState
        )
    }
}