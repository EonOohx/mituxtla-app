package com.eonoohx.mituxtlaapp.ui.model

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.local.CategoryType
import com.eonoohx.mituxtlaapp.data.local.PlaceCategories

data class PlacesUiState(
    val currentCategory: Int? = null,
    val categories: List<CategoryType> = PlaceCategories.listOfCategories,
)

data class FavoritePlaceUiState(
    val favoritePlacesList: List<FavoritePlace> = emptyList(),
    val favoritePlaceDetails: FavoritePlace? = null
)


sealed interface PlacesServiceUiState<out T> {
    data class Success<T>(val data: T) : PlacesServiceUiState<T>
    data object Error : PlacesServiceUiState<Nothing>
    data object Loading : PlacesServiceUiState<Nothing>
}