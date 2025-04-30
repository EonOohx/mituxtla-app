package com.eonoohx.mituxtlaapp.ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eonoohx.mituxtlaapp.MiTuxtlaApplication
import com.eonoohx.mituxtlaapp.data.DatabaseRepository
import com.eonoohx.mituxtlaapp.data.PlacesRepository
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.preference.AppTheme
import com.eonoohx.mituxtlaapp.data.preference.PreferenceRepository
import com.eonoohx.mituxtlaapp.ui.components.PlaceProperty
import com.eonoohx.mituxtlaapp.ui.utils.PlaceApiErrorType
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MiTuxtlaViewModel(
    private val placesRepository: PlacesRepository,
    private val databaseRepository: DatabaseRepository,
    private val userPreferencesRepository: PreferenceRepository
) : ViewModel() {

    private var _miTuxtlaUiState = MutableStateFlow(MiTuxtlaUiState())
    val miTuxtlaUiState: StateFlow<MiTuxtlaUiState> = _miTuxtlaUiState.asStateFlow()

    var listPlacesUiState: PlaceServiceUiState<List<Place>> by mutableStateOf(
        PlaceServiceUiState.Loading
    )
        private set

    var placeInfoUiState: PlaceServiceUiState<PlaceInfo> by mutableStateOf(
        PlaceServiceUiState.Loading
    )
        private set

    private val _favoritePlaceUiState = MutableStateFlow(FavoritePlaceUiState())
    val favoritePlaceUiState: StateFlow<FavoritePlaceUiState> =
        _favoritePlaceUiState.asStateFlow()

    fun setCurrentCategory(category: Int) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            currentCategory = category
        )
    }

    fun viewFavoritePlaceScreen(viewing: Boolean) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            forViewFavoritePlace = viewing
        )
    }

    fun onSortRequest(property: PlaceProperty) =
        _favoritePlaceUiState.update { currentUiState ->
            currentUiState.copy(
                favoritePlacesList = typeSorting(
                    property,
                    currentUiState.favoritePlacesList
                ),
                orderedBy = property
            )
        }

    private fun updateFavSaveState(state: Boolean) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            savingAsFavorite = state
        )
    }

    // DB OPERATIONS
    fun loadFavoritePlaces() = viewModelScope.launch {
        databaseRepository.getAllFavoritePlacesStream().collect { places ->
            _favoritePlaceUiState.update { currentUiState ->
                currentUiState.copy(
                    favoritePlacesList = typeSorting(
                        currentUiState.orderedBy,
                        places
                    ),
                )
            }
        }
    }

    fun loadFavoritePlace(placeId: String) = viewModelScope.launch {
        databaseRepository.updateFavoritePlaceStatus(placeId)

        val place = databaseRepository.getFavoritePlace(placeId).first()

        updateFavSaveState(true)

        _favoritePlaceUiState.update { currentUiState ->
            currentUiState.copy(
                favoritePlaceDetails = place,
            )
        }
    }

    fun savePlace(placeInfo: PlaceInfo, category: String) {
        viewModelScope.launch {
            databaseRepository.insertFavoritePlace(
                placeInfo.toFavPlace(category = category, viewed = getCurrentTimestamp())
            )

            updateFavSaveState(true)
        }
    }

    fun deleteFavoritePlace(placeId: String) {
        viewModelScope.launch {
            val place = databaseRepository.getFavoritePlace(placeId).first()
            databaseRepository.deleteFavoritePlace(place)

            updateFavSaveState(false)
        }

    }

    // API OPERATIONS
    fun getApiPlacesList(requestQuery: String) = viewModelScope.launch {
        listPlacesUiState = PlaceServiceUiState.Loading
        listPlacesUiState = apiCallHelper { placesRepository.getPlacesData(requestQuery) }
    }

    fun getApiPlaceInfo(placeId: String) = viewModelScope.launch {
        verifySavedPlace(databaseRepository.exists(placeId))
        placeInfoUiState = PlaceServiceUiState.Loading
        placeInfoUiState = apiCallHelper { placesRepository.getPlaceInfoData(placeId) }
    }

    private suspend fun <T> apiCallHelper(
        timeout: Long = 10000,
        call: suspend () -> T
    ): PlaceServiceUiState<T> {
        return try {
            withTimeout(timeout) {
                PlaceServiceUiState.Success(call())
            }
        } catch (e: IOException) {
            PlaceServiceUiState.Error(PlaceApiErrorType.NETWORK)
        } catch (e: HttpException) {
            PlaceServiceUiState.Error(PlaceApiErrorType.HTTP)
        } catch (e: TimeoutCancellationException) {
            PlaceServiceUiState.Error(PlaceApiErrorType.TIMEOUT)
        }
    }

    // Preferences
    val theme: StateFlow<AppTheme> =
        userPreferencesRepository.selectedTheme.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppTheme.LIGHT
        )

    fun selectTheme(theme: AppTheme) = viewModelScope.launch {
        userPreferencesRepository.saveThemePreference(theme)
    }

    // Utils
    private fun verifySavedPlace(exists: Boolean) {
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = exists
            )
        }
    }

    private fun typeSorting(
        property: PlaceProperty,
        list: List<FavoritePlace>
    ): List<FavoritePlace> {
        return when (property) {
            PlaceProperty.NAME -> list.sortedBy { it.name }
            PlaceProperty.VIEWED -> list.sortedByDescending { it.viewed }
            PlaceProperty.CATEGORY -> list.sortedBy { it.category }
        }
    }

    private fun getCurrentTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MiTuxtlaApplication)
                val placesRepository = application.container.placesRepository
                val databaseRepository = application.container.databaseRepository
                val userPreferencesRepository = application.userPreferencesRepository
                MiTuxtlaViewModel(
                    placesRepository = placesRepository,
                    databaseRepository = databaseRepository,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}