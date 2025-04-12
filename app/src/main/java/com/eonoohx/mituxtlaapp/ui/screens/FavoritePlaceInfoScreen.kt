package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaUiState
import com.eonoohx.mituxtlaapp.ui.model.toPlaceInfo

@Composable
fun FavoritePlaceInfoScreen(
    place: FavoritePlace?,
    miTuxtlaUiState: MiTuxtlaUiState,
    modifier: Modifier = Modifier,
    onDeleteAsFavorite: (placeId: String) -> Unit,
    onSavedAsFavorite: (place: PlaceInfo, category: String) -> Unit,
) {
    var markedAsFavorite by remember { mutableStateOf(miTuxtlaUiState.savingAsFavorite) }
    if (place != null) {
        PlaceInfoCard(
            data = place.toPlaceInfo(),
            isSavedAsFavorite = markedAsFavorite,
            onSavedFavorite = {
                markedAsFavorite = !markedAsFavorite
                if (miTuxtlaUiState.savingAsFavorite) {
                    if (!markedAsFavorite) onDeleteAsFavorite(place.id)
                } else {
                    if (markedAsFavorite) onSavedAsFavorite(place.toPlaceInfo(), place.category)
                }
            },
            modifier = modifier
        )
    }
}