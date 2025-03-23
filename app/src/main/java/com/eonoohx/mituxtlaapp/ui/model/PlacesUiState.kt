package com.eonoohx.mituxtlaapp.ui.model

import com.eonoohx.mituxtlaapp.network.Place

data class PlacesUiState(
    val currentCategory: String = "",
    val currentPlaceId: String = "",
    val places: List<Place> = emptyList(),
)