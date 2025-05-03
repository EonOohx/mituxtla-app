package com.eonoohx.mituxtlaapp.viewmodel

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.fake.FakeUserPreferenceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.components.PlaceProperty
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.utils.DataBaseErrorType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DbViewModelTest {
    private lateinit var miTuxtlaViewModel: MiTuxtlaViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private val fakeDBRepository = FakeFavoritePlaceDatabase()

    @Before
    fun setUp() {
        miTuxtlaViewModel = MiTuxtlaViewModel(
            placesRepository = FakeNetworkPlaceRepository(),
            databaseRepository = fakeDBRepository,
            userPreferencesRepository = FakeUserPreferenceRepository()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getFavoritePlaces_verifyListFavPlacesUiState() = runTest {
        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        val result = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
                as PlaceServiceUiState.Success

        assertEquals(
            FakeDataSource.fakeFavoritePlaces.toList().sortedBy { it.category },
            result.data
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getSingleFavPlace_verifyFavPlaceUiState() = runTest {
        miTuxtlaViewModel.loadFavoritePlace("place_001")

        advanceUntilIdle()

        val result = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
                as PlaceServiceUiState.Success

        assertEquals(
            FakeDataSource.fakeFavoritePlaces[0],
            result.data
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_saveFavoritePlace_verifySavedFavPlaceInUiState() = runTest {
        val mucKFavPlace = addFavoritePlace()

        miTuxtlaViewModel.loadFavoritePlace("place_004")
        advanceUntilIdle()

        val favoritePlace = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
                as PlaceServiceUiState.Success

        val muckFavPlace = mucKFavPlace.copy(viewed = favoritePlace.data.viewed)

        assertEquals(muckFavPlace, favoritePlace.data)
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
                as PlaceServiceUiState.Success

        assertTrue(places.data.size == 3)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_onSortRequest_verifyListSortingByCategory() = runTest {
        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        miTuxtlaViewModel.onSortRequest(PlaceProperty.CATEGORY)

        val places = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
                as PlaceServiceUiState.Success

        assertEquals(
            FakeDataSource.fakeFavoritePlaces.sortedBy { it.category },
            places.data
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_onSortRequest_verifyListSortingByName() = runTest {
        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        miTuxtlaViewModel.onSortRequest(PlaceProperty.NAME)

        val places = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
                as PlaceServiceUiState.Success

        assertEquals(
            FakeDataSource.fakeFavoritePlaces.sortedBy { it.name },
            places.data
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_onSortRequest_verifyListSortingByViewed() = runTest {
        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        miTuxtlaViewModel.onSortRequest(PlaceProperty.VIEWED)

        val places = miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
                as PlaceServiceUiState.Success

        assertEquals(
            FakeDataSource.fakeFavoritePlaces.sortedByDescending { it.viewed },
            places.data
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getApiPlaceInfo_verifyIsSaveAsFavorite() = runTest {
        addFavoritePlace()

        miTuxtlaViewModel.getApiPlaceInfo("place_004")

        advanceUntilIdle()

        assertTrue(miTuxtlaViewModel.miTuxtlaUiState.value.savingAsFavorite)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getFavoritePlaces_verifyIOError() = runTest {
        fakeDBRepository.throwDBError = DataBaseErrorType.IO

        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Error(DataBaseErrorType.IO),
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getFavoritePlaces_verifyCorruptError() = runTest {
        fakeDBRepository.throwDBError = DataBaseErrorType.CORRUPT

        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Error(DataBaseErrorType.CORRUPT),
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getSingleFavPlace_verifyDiskError() = runTest {
        fakeDBRepository.throwDBError = DataBaseErrorType.DISK

        miTuxtlaViewModel.loadFavoritePlace("place_001")
        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Error(DataBaseErrorType.DISK),
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getSingleFavPlace_verifyInaccessibilityError() = runTest {
        fakeDBRepository.throwDBError = DataBaseErrorType.INACCESSIBLE

        miTuxtlaViewModel.loadFavoritePlace("place_001")

        advanceTimeBy(1000)
        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(DataBaseErrorType.INACCESSIBLE),
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlaceDetails
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getFavoritePlace_verifyEmptyList() = runTest {
        for (place in FakeDataSource.fakeFavoritePlaces) {
            miTuxtlaViewModel.deleteFavoritePlace(place.id)
        }

        miTuxtlaViewModel.loadFavoritePlaces()

        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Success<List<FavoritePlace>>(emptyList()),
            miTuxtlaViewModel.favoritePlaceUiState.value.favoritePlacesList
        )
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