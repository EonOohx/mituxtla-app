package com.eonoohx.mituxtlaapp.ui.model

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.local.CategoryType
import com.eonoohx.mituxtlaapp.data.local.PlaceCategories
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.network.PlaceLocation
import com.eonoohx.mituxtlaapp.ui.components.PlaceProperty
import com.eonoohx.mituxtlaapp.ui.utils.RepositoryErrorType

data class MiTuxtlaUiState(
    val currentCategory: Int? = null,
    val categories: List<CategoryType> = PlaceCategories.listOfCategories,
    val forViewFavoritePlace: Boolean = false,
    val savingAsFavorite: Boolean = false,
)

data class FavoritePlaceUiState(
    val favoritePlaceDetails: PlaceServiceUiState<FavoritePlace> = PlaceServiceUiState.Loading,
    val favoritePlacesList: PlaceServiceUiState<List<FavoritePlace>> = PlaceServiceUiState.Loading,
    val orderedBy: PlaceProperty = PlaceProperty.CATEGORY
)

sealed interface PlaceServiceUiState<out T> {
    data class Success<T>(val data: T) : PlaceServiceUiState<T>
    data class Error(val error: RepositoryErrorType) : PlaceServiceUiState<Nothing>
    data object Loading : PlaceServiceUiState<Nothing>
}

fun PlaceInfo.toFavPlace(category: String, viewed: String): FavoritePlace = FavoritePlace(
    id = id,
    name = name,
    address = address,
    description = description,
    photoUrl = photoUrl,
    latLocation = location?.lat.toString(),
    lngLocation = location?.lng.toString(),
    phone = phone,
    website = website,
    viewed = viewed,
    category = category,
    rating = 0.0F
)

fun FavoritePlace.toPlaceInfo(): PlaceInfo {
    val location = if (latLocation != null && lngLocation != null) PlaceLocation(
        lat = latLocation.toDouble(),
        lng = lngLocation.toDouble()
    ) else null

    return PlaceInfo(
        id = id,
        name = name,
        address = address ?: "",
        description = description,
        photoUrl = photoUrl,
        rating = 0.0f,
        isOpen = true,
        location = location,
        phone = phone,
        website = website ?: "",
    )
}
