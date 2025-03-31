package com.eonoohx.mituxtlaapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.network.Place
import com.eonoohx.mituxtlaapp.ui.components.ContentText
import com.eonoohx.mituxtlaapp.ui.model.PlacesServiceUiState
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun PlacesScreen(
    placesServiceUiState: PlacesServiceUiState<List<Place>>,
    modifier: Modifier = Modifier,
    onSelectOptionClicked: (String) -> Unit,
) {
    when (placesServiceUiState) {
        is PlacesServiceUiState.Loading -> LoadingScreen()
        is PlacesServiceUiState.Success -> PlacesGridScreen(
            places = placesServiceUiState.data,
            modifier = modifier,
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
        ) { placeId -> onSelectOptionClicked(placeId) }

        is PlacesServiceUiState.Error -> ErrorScreen()
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
fun PlacesContentScreen(text: String, imageSrc: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imageSrc)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.img_loading),
            contentDescription = text,
            contentScale = ContentScale.Crop,
            modifier = Modifier.aspectRatio(1f)
        )
        ContentText(
            text = text,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f)),
            isOnMenu = false
        )
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