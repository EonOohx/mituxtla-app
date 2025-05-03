package com.eonoohx.mituxtlaapp.ui.model

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private fun updateFavSaveState(state: Boolean) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            savingAsFavorite = state
        )
    }

    // DB OPERATIONS
    fun loadFavoritePlaces() = viewModelScope.launch {
        dbCallHelper(
            call = {
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
            },
            onError = { errorState ->
                _favoritePlaceUiState.update { currentUiState ->
                    currentUiState.copy(
                        favoritePlacesList = errorState
                    )
                }
            }
        )
    }

    fun loadFavoritePlace(placeId: String) = viewModelScope.launch {
        dbCallHelper(
            call = {
                updateFavSaveState(true)
                databaseRepository.updateFavoritePlaceStatus(placeId)
                val place = databaseRepository.getFavoritePlace(placeId).first()
                _favoritePlaceUiState.update {
                    it.copy(favoritePlaceDetails = PlaceServiceUiState.Success(place))
                }
            },
            onError = { errorState ->
                _favoritePlaceUiState.update { currentUiState ->
                    currentUiState.copy(favoritePlaceDetails = errorState)
                }
            }
        )
    }

    fun savePlace(placeInfo: PlaceInfo, category: String) =
        viewModelScope.launch {
            dbCallHelper(
                call = {
                    databaseRepository.insertFavoritePlace(
                        placeInfo.toFavPlace(category = category, viewed = getCurrentTimestamp())
                    )

                    updateFavSaveState(true)
                },
            )
        }

    fun deleteFavoritePlace(placeId: String) = viewModelScope.launch {
        dbCallHelper(
            call = {
                val place = databaseRepository.getFavoritePlace(placeId).first()
                databaseRepository.deleteFavoritePlace(place)

                updateFavSaveState(false)
            },
        )
    }

    private fun typeSorting(
        property: PlaceProperty,
        list: List<FavoritePlace>
    ): PlaceServiceUiState.Success<List<FavoritePlace>> {
        return PlaceServiceUiState.Success(
            when (property) {
                PlaceProperty.NAME -> list.sortedBy { it.name }
                PlaceProperty.VIEWED -> list.sortedByDescending { it.viewed }
                PlaceProperty.CATEGORY -> list.sortedBy { it.category }
            })
    }

    fun onSortRequest(property: PlaceProperty) {
        _favoritePlaceUiState.update { currentUiState ->
            currentUiState.copy(
                favoritePlacesList =
                    typeSorting(
                        property,
                        (currentUiState.favoritePlacesList as PlaceServiceUiState.Success).data
                    ),
                orderedBy = property
            )
        }
    }

    // API OPERATIONS
    fun getApiPlacesList(requestQuery: String) = viewModelScope.launch {
        listPlacesUiState = PlaceServiceUiState.Loading
        listPlacesUiState = apiCallHelper { placesRepository.getPlacesData(requestQuery) }
    }

    fun getApiPlaceInfo(placeId: String) {
        viewModelScope.launch {
            dbCallHelper(call = { verifySavedPlace(databaseRepository.exists(placeId)) })
            placeInfoUiState = PlaceServiceUiState.Loading
            placeInfoUiState = apiCallHelper {
                placesRepository.getPlaceInfoData(placeId)
            }
        }
    }

    private fun verifySavedPlace(exists: Boolean) {
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = exists
            )
        }
    }

    // PREFERENCES
    val theme: StateFlow<AppTheme> =
        userPreferencesRepository.selectedTheme.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppTheme.LIGHT
        )

    fun selectTheme(theme: AppTheme) = viewModelScope.launch {
        userPreferencesRepository.saveThemePreference(theme)
    }

    // UTILS
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