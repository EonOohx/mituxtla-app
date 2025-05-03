package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.ui.components.ErrorScreen
import com.eonoohx.mituxtlaapp.ui.components.LoadingScreen
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaUiState
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.model.toPlaceInfo
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

@Composable
fun FavoritePlaceInfoScreen(
    place: PlaceServiceUiState<FavoritePlace>,
    miTuxtlaUiState: MiTuxtlaUiState,
    modifier: Modifier = Modifier,
    onDeleteAsFavorite: (placeId: String) -> Unit,
    onSavedAsFavorite: (place: PlaceInfo, category: String) -> Unit,
) {
    var markedAsFavorite by remember { mutableStateOf(miTuxtlaUiState.savingAsFavorite) }

    when (place) {
        is PlaceServiceUiState.Error -> {
            ErrorScreen(error = place.error)
        }

        PlaceServiceUiState.Loading -> {
            LoadingScreen()
        }

        is PlaceServiceUiState.Success -> {
            FavoritePlaceInfoCard(
                place = place.data,
                markedAsFavorite = markedAsFavorite,
                modifier = modifier
            ) {
                markedAsFavorite = !markedAsFavorite
                if (miTuxtlaUiState.savingAsFavorite) {
                    if (!markedAsFavorite) onDeleteAsFavorite(place.data.id)
                } else {
                    if (markedAsFavorite) onSavedAsFavorite(
                        place.data.toPlaceInfo(),
                        place.data.category
                    )
                }
            }
        }
    }

}

@Composable
fun FavoritePlaceInfoCard(
    place: FavoritePlace,
    markedAsFavorite: Boolean,
    modifier: Modifier = Modifier,
    onSaveAsFavorite: () -> Unit
) {

    PlaceInfoCard(
        data = place.toPlaceInfo(),
        isSavedAsFavorite = markedAsFavorite,
        onSavedFavorite = onSaveAsFavorite,
        modifier = modifier
    )
}

@Preview
@Composable
fun FavoritePlaceInfoCardPreview() {
    MiTuxtlaAppTheme {
        val place = FavoritePlace(
            id = "place_004",
            name = "Golden Gate Bridge",
            rating = 0.0f,
            photoUrl = "https://example.com/photos/golden_gate.jpg",
            address = "Golden Gate Bridge, San Francisco, CA, USA",
            description = "A suspension bridge spanning the Golden Gate strait, " +
                    "the one-mile-wide channel between San Francisco Bay and the Pacific Ocean.",
            website = null,
            phone = null,
            latLocation = "37.819929",
            lngLocation = "-122.478255",
            category = "Kid-Friendly Places",
            viewed = ""
        )

        FavoritePlaceInfoCard(
            place = place,
            markedAsFavorite = true,
        ) {}
    }
}