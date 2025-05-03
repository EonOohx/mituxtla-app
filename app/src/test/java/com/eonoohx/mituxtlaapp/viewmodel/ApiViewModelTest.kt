package com.eonoohx.mituxtlaapp.viewmodel

import com.eonoohx.mituxtlaapp.fake.FakeDataSource
import com.eonoohx.mituxtlaapp.fake.FakeFavoritePlaceDatabase
import com.eonoohx.mituxtlaapp.fake.FakeNetworkPlaceRepository
import com.eonoohx.mituxtlaapp.fake.FakeUserPreferenceRepository
import com.eonoohx.mituxtlaapp.rules.TestDispatcherRule
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.model.TIMEOUT
import com.eonoohx.mituxtlaapp.ui.utils.ApiErrorType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApiViewModelTest {
    private lateinit var miTuxtlaViewModel: MiTuxtlaViewModel

    @get: Rule
    val testDispatcherRule = TestDispatcherRule()

    private val fakeApiRepository = FakeNetworkPlaceRepository()

    @Before
    fun setUp() {
        miTuxtlaViewModel = MiTuxtlaViewModel(
            placesRepository = fakeApiRepository,
            databaseRepository = FakeFavoritePlaceDatabase(),
            userPreferencesRepository = FakeUserPreferenceRepository(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placesViewModel_getPlaces_verifyListPlacesUiSateSuccess() = runTest {
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
        fakeApiRepository.apiError = ApiErrorType.HTTP

        miTuxtlaViewModel.getApiPlacesList("")

        advanceUntilIdle()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.HTTP),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getListOfPlaces_networkException() = runTest {
        fakeApiRepository.apiError = ApiErrorType.NETWORK

        miTuxtlaViewModel.getApiPlacesList("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.NETWORK),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getListOfPlaces_timeoutException() = runTest {
        fakeApiRepository.apiError = ApiErrorType.TIMEOUT

        miTuxtlaViewModel.getApiPlacesList("")

        advanceTimeBy(TIMEOUT + 1)
        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.TIMEOUT),
            miTuxtlaViewModel.listPlacesUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_httpException() = runTest {
        fakeApiRepository.apiError = ApiErrorType.HTTP

        miTuxtlaViewModel.getApiPlaceInfo("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.HTTP),
            miTuxtlaViewModel.placeInfoUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_networkException() = runTest {
        fakeApiRepository.apiError = ApiErrorType.NETWORK

        miTuxtlaViewModel.getApiPlaceInfo("")

        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.NETWORK),
            miTuxtlaViewModel.placeInfoUiState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun placeViewModel_getPlaceInfo_timeoutException() = runTest {
        fakeApiRepository.apiError = ApiErrorType.TIMEOUT

        miTuxtlaViewModel.getApiPlaceInfo("")

        advanceTimeBy(TIMEOUT + 1)
        runCurrent()

        assertEquals(
            PlaceServiceUiState.Error(ApiErrorType.TIMEOUT),
            miTuxtlaViewModel.placeInfoUiState
        )
    }
}