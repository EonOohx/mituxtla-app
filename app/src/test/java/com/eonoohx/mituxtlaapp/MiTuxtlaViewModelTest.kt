package com.eonoohx.mituxtlaapp

import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MiTuxtlaViewModelTest {
    private lateinit var miTuxtlaViewModel: MiTuxtlaViewModel

    @get: Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        miTuxtlaViewModel = MiTuxtlaViewModel(
            placesRepository = FakeNetworkPlaceRepository(),
            databaseRepository = FakeFavoritePlaceDatabase()
        )
    }

    @Test
    fun placesViewModel_getPlaces_verifyListPlacesUiSateSuccess() = runTest {
        miTuxtlaViewModel.getApiPlacesList("")
        assertEquals(
            PlaceServiceUiState.Success(FakeDataSource.fakePlaces),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @Test
    fun placesViewModel_getPlaceInfo_verifyPlaceInfoUiState() = runTest {
        miTuxtlaViewModel.getApiPlaceInfo("")
        assertEquals(
            PlaceServiceUiState.Success(FakeDataSource.fakePlaceInfo),
            miTuxtlaViewModel.placeInfoUiState
        )
    }
}