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
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MiTuxtlaViewModel(
    private val placesRepository: PlacesRepository,
    private val databaseRepository: DatabaseRepository
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


    private val _favoritePlaceDetailsUiState = MutableStateFlow(FavoritePlaceDetailsUiState())

    val favoritePlaceDetailsUiState: StateFlow<FavoritePlaceDetailsUiState> =
        _favoritePlaceDetailsUiState.asStateFlow()

    val favoritePlaceUiState: StateFlow<FavoritePlaceUiState> =
        databaseRepository.getAllFavoritePlacesStream()
            .map { placeList -> FavoritePlaceUiState(favoritePlacesList = placeList) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoritePlaceUiState()
            )

    fun loadFavoritePlace(placeId: String) {
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = true
            )
        }
        viewModelScope.launch {
            databaseRepository.updateFavoritePlaceStatus(placeId)
            val place = databaseRepository.getFavoritePlace(placeId).first()
            _favoritePlaceDetailsUiState.value =
                FavoritePlaceDetailsUiState(favoritePlaceDetails = place)
        }
    }

    fun savePlace(placeInfo: PlaceInfo, category: String) = viewModelScope.launch {
        databaseRepository.insertFavoritePlace(
            placeInfo.toFavPlace(category = category, viewed = getCurrentTimestamp())
        )
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = true
            )
        }
    }

    fun deleteFavoritePlace(placeId: String) = viewModelScope.launch {
        val place = databaseRepository.getFavoritePlace(placeId).first()
        databaseRepository.deleteFavoritePlace(place)
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = false
            )
        }
    }

    fun setCurrentCategory(category: Int) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            currentCategory = category
        )
    }

    fun viewFavoritePlace(viewing: Boolean) = _miTuxtlaUiState.update { currentUiState ->
        currentUiState.copy(
            forViewFavoritePlace = viewing
        )
    }

    // API OPERATIONS
    fun getApiPlacesList(requestQuery: String) = viewModelScope.launch {
        listPlacesUiState = PlaceServiceUiState.Loading
        listPlacesUiState = try {
            PlaceServiceUiState.Success(
                data = placesRepository.getPlacesData(
                    search = requestQuery
                )
            )
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlaceServiceUiState.Error
        } catch (e: HttpException) {
            Log.e("Network Error", e.toString())
            PlaceServiceUiState.Error
        }
    }

    fun getApiPlaceInfo(placeId: String) = viewModelScope.launch {
        verifySavedPlace(databaseRepository.exists(placeId))
        placeInfoUiState = PlaceServiceUiState.Loading
        placeInfoUiState = try {
            PlaceServiceUiState.Success(
                data = placesRepository.getPlaceInfoData(placeId = placeId)
            )
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlaceServiceUiState.Error
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlaceServiceUiState.Error
        }
    }

    // Utils
    private fun verifySavedPlace(exists: Boolean) {
        _miTuxtlaUiState.update { currentUiState ->
            currentUiState.copy(
                savingAsFavorite = exists
            )
        }
    }

    private fun getCurrentTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 20_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MiTuxtlaApplication)
                val placesRepository = application.container.placesRepository
                val databaseRepository = application.container.databaseRepository
                MiTuxtlaViewModel(
                    placesRepository = placesRepository,
                    databaseRepository = databaseRepository
                )
            }
        }
    }
}