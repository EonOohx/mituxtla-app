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
import com.eonoohx.mituxtlaapp.data.PlacesRepository
import com.eonoohx.mituxtlaapp.network.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PlacesServiceUiState {
    data class Success(val placesList: List<Place>) : PlacesServiceUiState
    data object Error : PlacesServiceUiState
    data object Loading : PlacesServiceUiState
}

class PlacesViewModel(private val placesRepository: PlacesRepository) : ViewModel() {
    var placesServiceUiState: PlacesServiceUiState by mutableStateOf(PlacesServiceUiState.Loading)
        private set

    private var _placesUiState = MutableStateFlow(PlacesUiState())
    val placesUiState: StateFlow<PlacesUiState> = _placesUiState.asStateFlow()

    fun getPlacesList(query: String) {
        viewModelScope.launch {
            placesServiceUiState = PlacesServiceUiState.Loading
            placesServiceUiState = try {
                val placesList = placesRepository.getPlacesData(placesSearch = query)

                _placesUiState.update { currentUiState ->
                    currentUiState.copy(
                        places = placesList
                    )
                }

                PlacesServiceUiState.Success(placesList = placesList)
            } catch (e: IOException) {
                Log.e("Network Error", e.toString())
                PlacesServiceUiState.Error
            } catch (e: HttpException) {
                Log.e("Network Error", e.toString())
                PlacesServiceUiState.Error
            }
        }
    }

    fun setCurrentPlace(id: String) {
        _placesUiState.update { currentUiState ->
            currentUiState.copy(
                currentPlaceId = id
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MiTuxtlaApplication)
                val placesRepository = application.container.placesRepository
                PlacesViewModel(placesRepository = placesRepository)
            }
        }
    }
}