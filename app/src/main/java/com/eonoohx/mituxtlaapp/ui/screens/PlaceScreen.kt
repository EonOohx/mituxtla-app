package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.ui.components.ErrorScreen
import com.eonoohx.mituxtlaapp.ui.components.LoadingScreen
import com.eonoohx.mituxtlaapp.ui.components.PlacesContentScreen
import com.eonoohx.mituxtlaapp.ui.model.PlaceServiceUiState
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun PlacesScreen(
    placeServiceUiState: PlaceServiceUiState<List<Place>>,
    modifier: Modifier = Modifier,
    onSelectOptionClicked: (String) -> Unit,
) {
    when (placeServiceUiState) {
        is PlaceServiceUiState.Loading -> LoadingScreen()
        is PlaceServiceUiState.Success -> PlacesGridScreen(
            places = placeServiceUiState.data,
            modifier = modifier,
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
        ) { placeId -> onSelectOptionClicked(placeId) }

        is PlaceServiceUiState.Error -> ErrorScreen()
    }
}

@Composable
fun PlacesGridScreen(
    places: List<Place>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSelectOptionClicked: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        items(items = places, key = { place -> place.id }) { place ->
            Card(
                shape = Shape.small,
                onClick = { onSelectOptionClicked(place.id) },
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
@Preview(showBackground = true)
fun PlacesContentPreview() {
    val mockPlacesData = List(6) {
        Place(
            id = "$it", name = "place", rating = 0.0f, photoUrl =
            ""
        )
    }
    MiTuxtlaAppTheme {
        PlacesGridScreen(
            places = mockPlacesData,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
        ) { }
    }
}