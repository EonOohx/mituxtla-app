package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.ui.components.ErrorScreen
import com.eonoohx.mituxtlaapp.ui.components.LoadingScreen
import com.eonoohx.mituxtlaapp.ui.components.PlacesContentScreen
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun FavoritePlaceScreen(
    favoritePlaces: PlaceServiceUiState<List<FavoritePlace>>,
    modifier: Modifier = Modifier,
    onSelectedFavoritePlace: (id: String) -> Unit,
) {
    when (favoritePlaces) {
        is PlaceServiceUiState.Error -> {
            ErrorScreen(error = favoritePlaces.error)
        }

        PlaceServiceUiState.Loading -> {
            LoadingScreen()
        }

        is PlaceServiceUiState.Success -> {
            if (favoritePlaces.data.isEmpty()) FavoritePlaceEmptyGrid(modifier = modifier) else {
                FavoritePlaceGrid(
                    favoritePlaces = favoritePlaces.data,
                    modifier = modifier,
                    onClick = onSelectedFavoritePlace
                )
            }

        }
    }
}

@Composable
fun FavoritePlaceEmptyGrid(modifier: Modifier = Modifier) {
    Column(modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            Image(
                painter = painterResource(R.drawable.empty_inbox),
                contentDescription = stringResource(R.string.nothing_here_yet),
                modifier.size(
                    dimensionResource(R.dimen.size_large)
                )
            )
            Text(
                text = stringResource(R.string.nothing_here_yet),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FavoritePlaceGrid(
    favoritePlaces: List<FavoritePlace>,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
    ) {
        items(
            items = favoritePlaces,
            key = { place -> place.id }) { place ->
            Card(
                shape = Shape.small,
                onClick = { onClick(place.id) },
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
    FavoritePlaceGrid(
        favoritePlaces = mockFavoritePlaces,
        onClick = {},
        modifier = Modifier.safeDrawingPadding()
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FavoritePlaceEmptyScreenPreview() {
    FavoritePlaceEmptyGrid(
        modifier = Modifier.safeDrawingPadding()
    )
}