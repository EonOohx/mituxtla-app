package com.eonoohx.mituxtlaapp.viewmodel

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.fake.FakeUserPreferenceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelDataBaseTest {
    private lateinit var miTuxtlaViewModel: MiTuxtlaViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        miTuxtlaViewModel = MiTuxtlaViewModel(
            placesRepository = FakeNetworkPlaceRepository(),
            databaseRepository = FakeFavoritePlaceDatabase(),
            userPreferencesRepository = FakeUserPreferenceRepository()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getFavoritePlaces_verifyListFavPlacesUiState() = runTest {
        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        assertEquals(
            FakeDataSource.fakeFavoritePlaces.toList().sortedBy { it.category },
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getSingleFavPlace_verifyFavPlaceUiState() = runTest {
        miTuxtlaViewModel.loadFavoritePlace("place_001")

        advanceUntilIdle()

        assertEquals(
            FakeDataSource.fakeFavoritePlaces[0],
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_saveFavoritePlace_verifySavedFavPlaceInUiState() = runTest {
        val mucKFavPlace = addFavoritePlace()

        miTuxtlaViewModel.loadFavoritePlace("place_004")
        advanceUntilIdle()

        val favoritePlace = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
        val muckFavPlace = mucKFavPlace.copy(viewed = favoritePlace?.viewed ?: "")

        assertEquals(muckFavPlace, favoritePlace)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_deleteFavoritePlace_verifyFavPlaceDoesNotExist() = runTest {
        addFavoritePlace()

        miTuxtlaViewModel.loadFavoritePlace("place_004")
        advanceUntilIdle()

        assertNotNull(miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails)

        miTuxtlaViewModel.deleteFavoritePlace("place_004")
        miTuxtlaViewModel.loadFavoritePlaces()
        advanceUntilIdle()

        val places = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
        assertTrue(places.size == 3)
    }

    private fun addFavoritePlace(): FavoritePlace {
        val mucKFavPlace = FakeDataSource.favoriteFromInfo

        miTuxtlaViewModel.savePlace(
            placeInfo = FakeDataSource.fakePlaceInfo,
            category = mucKFavPlace.category
        )

        return mucKFavPlace
    }

}