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
import com.eonoohx.mituxtlaapp.network.PlaceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PlacesServiceUiState<out T> {
    data class Success<T>(val data: T) : PlacesServiceUiState<T>
    data object Error : PlacesServiceUiState<Nothing>
    data object Loading : PlacesServiceUiState<Nothing>
}

class PlacesViewModel(private val placesRepository: PlacesRepository) : ViewModel() {
   var listPlacesUiState: PlacesServiceUiState<List<Place>> by mutableStateOf(
        PlacesServiceUiState.Loading)
        private set

    var placeInformationUiState: PlacesServiceUiState<PlaceInfo> by mutableStateOf(
        PlacesServiceUiState.Loading)
        private set

    private var _placesUiState = MutableStateFlow(PlacesUiState())
    val placesUiState: StateFlow<PlacesUiState> = _placesUiState.asStateFlow()

    fun getPlacesList(query: String) {
        viewModelScope.launch {
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
    }

    fun getPlaceInfo(placeId: String) {
        viewModelScope.launch {
            placeInformationUiState= PlacesServiceUiState.Loading
            placeInformationUiState = try {
                PlacesServiceUiState.Success(
                    data = placesRepository.getPlaceInfoData(placeId = placeId)
                )
            }catch (e: IOException) {
                Log.e("Network Error", e.toString())
                PlacesServiceUiState.Error
            } catch (e: IOException) {
                Log.e("Network Error", e.toString())
                PlacesServiceUiState.Error
            }
        }
    }

    fun setCurrentCategory(category: Int) {
        _placesUiState.update { currentUiState ->
            currentUiState.copy(
                currentCategory = category
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