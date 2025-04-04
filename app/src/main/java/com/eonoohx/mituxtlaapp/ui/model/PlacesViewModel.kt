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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlacesViewModel(
    private val placesRepository: PlacesRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    var listPlacesUiState: PlacesServiceUiState<List<Place>> by mutableStateOf(
        PlacesServiceUiState.Loading
    )
        private set

    var placeInformationUiState: PlacesServiceUiState<PlaceInfo> by mutableStateOf(
        PlacesServiceUiState.Loading
    )
        private set

    private var _favoritePlacesUiState = MutableStateFlow(FavoritePlaceUiState())
    val favoritePlaceUiState: StateFlow<FavoritePlaceUiState> = _favoritePlacesUiState.asStateFlow()

    private var _placesUiState = MutableStateFlow(PlacesUiState())
    val placesUiState: StateFlow<PlacesUiState> = _placesUiState.asStateFlow()


    fun getPlacesList(query: String) = viewModelScope.launch {
        listPlacesUiState = PlacesServiceUiState.Loading
        listPlacesUiState = try {
            PlacesServiceUiState.Success(
                data = placesRepository.getPlacesData(
                    search = query
                )
            )
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlacesServiceUiState.Error
        } catch (e: HttpException) {
            Log.e("Network Error", e.toString())
            PlacesServiceUiState.Error
        }
    }

    fun getPlaceInfo(placeId: String) = viewModelScope.launch {
        placeInformationUiState = PlacesServiceUiState.Loading
        placeInformationUiState = try {
            PlacesServiceUiState.Success(
                data = placesRepository.getPlaceInfoData(placeId = placeId)
            )
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlacesServiceUiState.Error
        } catch (e: IOException) {
            Log.e("Network Error", e.toString())
            PlacesServiceUiState.Error
        }
    }

    fun setCurrentCategory(category: Int) = _placesUiState.update { currentUiState ->
        currentUiState.copy(
            currentCategory = category
        )
    }

    fun savePlace(placeInfo: PlaceInfo, category: String) = viewModelScope.launch {
        databaseRepository.insertFavoritePlace(
            FavoritePlace(
                id = placeInfo.id,
                name = placeInfo.name,
                address = placeInfo.address,
                description = placeInfo.description,
                photoUrl = placeInfo.photoUrl,
                latLocation = placeInfo.location?.lat.toString(),
                lngLocation = placeInfo.location?.lng.toString(),
                phone = placeInfo.phone,
                website = placeInfo.website,
                viewed = getCurrentTimestamp(),
                category = category
            )
        )
    }

    fun getAllFavoritePlaces() =
        databaseRepository.getAllFavoritePlacesStream()
            .map { FavoritePlaceUiState(favoritePlacesList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoritePlaceUiState()
            )

    fun getFavoritePlace(id: String) =
        databaseRepository.getFavoritePlace(id).filterNotNull()
            .map { FavoritePlaceUiState(favoritePlaceDetails = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoritePlaceUiState()
            )

    fun updateViewedStatus(id: String) = viewModelScope.launch {
        databaseRepository.updateFavoritePlaceStatus(id)
    }

    fun deleteFavoritePlace() = viewModelScope.launch {
        val favoritePlace = _favoritePlacesUiState.value.favoritePlaceDetails
        if (favoritePlace != null) databaseRepository.deleteFavoritePlace(favoritePlace)
    }

    private fun getCurrentTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MiTuxtlaApplication)
                val placesRepository = application.container.placesRepository
                val databaseRepository = application.container.databaseRepository
                PlacesViewModel(
                    placesRepository = placesRepository,
                    databaseRepository = databaseRepository
                )
            }
        }
    }
}