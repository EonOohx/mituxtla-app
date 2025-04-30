package com.eonoohx.mituxtlaapp.viewmodel

import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.fake.FakeUserPreferenceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.utils.PlaceApiErrorType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ViewModelApiTest {
    private lateinit var miTuxtlaViewModel: MiTuxtlaViewModel

    @get: Rule
    val testDispatcherRule = TestDispatcherRule()

    private fun setUp(
        httpError: Boolean = false,
        networkError: Boolean = false,
        timeoutError: Boolean = false
    ) {
        miTuxtlaViewModel = MiTuxtlaViewModel(
            placesRepository = FakeNetworkPlaceRepository(
                httpError = httpError,
                networkError = networkError,
                timeoutError = timeoutError
            ),
            databaseRepository = FakeFavoritePlaceDatabase(),
            userPreferencesRepository = FakeUserPreferenceRepository(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getPlaces_verifyListPlacesUiSateSuccess() = runTest {

        setUp()

        miTuxtlaViewModel.getApiPlacesList("")

        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Success(FakeDataSource.fakePlaces),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getPlaceInfo_verifyPlaceInfoUiState() = runTest {

        setUp()

        miTuxtlaViewModel.getApiPlaceInfo("")
        advanceUntilIdle()
        assertEquals(
            PlaceServiceUiState.Success(FakeDataSource.fakePlaceInfo),
            miTuxtlaViewModel.placeInfoUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getListOfPlaces_httpException() = runTest {
        setUp(httpError = true)

        miTuxtlaViewModel.getApiPlacesList("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.HTTP),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getListOfPlaces_networkException() = runTest {
        setUp(networkError = true)

        miTuxtlaViewModel.getApiPlacesList("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.NETWORK),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getListOfPlaces_timeoutException() = runTest {
        setUp(timeoutError = true)

        miTuxtlaViewModel.getApiPlacesList("")

        advanceTimeBy(12000)
        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.TIMEOUT),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_httpException() = runTest {
        setUp(httpError = true)

        miTuxtlaViewModel.getApiPlaceInfo("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.HTTP),
            miTuxtlaViewModel.placeInfoUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_networkException() = runTest {
        setUp(networkError = true)

        miTuxtlaViewModel.getApiPlaceInfo("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.NETWORK),
            miTuxtlaViewModel.placeInfoUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_timeoutException() = runTest {
        setUp(timeoutError = true)

        miTuxtlaViewModel.getApiPlaceInfo("")

        advanceTimeBy(12000)
        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(PlaceApiErrorType.TIMEOUT),
            miTuxtlaViewModel.placeInfoUiState
        )
    }
}