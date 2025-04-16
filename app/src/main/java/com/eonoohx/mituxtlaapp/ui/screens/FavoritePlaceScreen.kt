package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.ui.components.PlacesContentScreen
import com.eonoohx.mituxtlaapp.ui.model.FavoritePlaceUiState
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun FavoritePlaceScreen(
    favoritePlaceUiState: State<FavoritePlaceUiState>,
    modifier: Modifier = Modifier,
    onSelectedFavoritePlace: (id: String) -> Unit,
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(
            items = favoritePlaceUiState.value.favoritePlacesList,
            key = { place -> place.id }) { place ->
            Card(
                shape = Shape.small,
                onClick = { onSelectedFavoritePlace(place.id) },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_tiny))
            ) {
                PlacesContentScreen(
                    text = place.name,
                    imageSrc = place.photoUrl
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FavoritePlaceScreenPreview() {
    val mockFavoritePlaces = listOf(
        FavoritePlace(
            id = "place_001",
            name = "Central Park",
            category = "Park",
            photoUrl = "https://example.com/photos/central_park.jpg",
            viewed = "2025-04-01",
            address = "New York, NY 10022, USA",
            description = "A large public park in New York City, great for walking, picnics, and relaxing.",
            latLocation = "40.785091",
            lngLocation = "-73.968285",
            phone = "+1 212-310-6600",
            website = "https://www.centralparknyc.org",
            rating = 0.0f
        ),
    )
    FavoritePlaceScreen(
        favoritePlaceUiState = remember {
            mutableStateOf(FavoritePlaceUiState(favoritePlacesList = mockFavoritePlaces)) },
        onSelectedFavoritePlace = {},
        modifier = Modifier.safeDrawingPadding()
    )
}