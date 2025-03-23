package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.network.Place
import com.eonoohx.mituxtlaapp.ui.components.ContentText
import com.eonoohx.mituxtlaapp.ui.model.PlacesServiceUiState
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun PlacesScreen(
    placesServiceUiState: PlacesServiceUiState,
    contentList: List<Place>,
    modifier: Modifier = Modifier,
    onSelectOptionClicked: (String) -> Unit,
) {

    when (placesServiceUiState) {
        is PlacesServiceUiState.Loading -> LoadingScreen()
        is PlacesServiceUiState.Success -> LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
        ) {
            items(contentList) { element ->
                Card(shape = Shape.small, onClick = { onSelectOptionClicked(element.id) }) {
                    PlacesContentScreen(
                        text = element.name,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_tiny))
                    )
                }
            }
        }

        is PlacesServiceUiState.Error -> ErrorScreen()
    }
}

@Composable
fun PlacesContentScreen(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(R.drawable.ic_loading),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.White)
        )
        ContentText(
            text = text,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f)),
            isOnMenu = false
        )
    }
}