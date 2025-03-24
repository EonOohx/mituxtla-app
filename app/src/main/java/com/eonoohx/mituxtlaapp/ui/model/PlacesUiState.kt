package com.eonoohx.mituxtlaapp.ui.model

import com.eonoohx.mituxtlaapp.data.local.CategoryType
import com.eonoohx.mituxtlaapp.data.local.PlaceCategories

data class PlacesUiState(
    val currentCategory: Int? = null,
    val categories: List<CategoryType> = PlaceCategories.listOfCategories
)