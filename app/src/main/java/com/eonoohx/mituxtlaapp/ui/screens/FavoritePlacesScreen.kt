package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.ui.components.PlacesContentScreen
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun FavoritePlacesScreen(
    listFavoritePlaces: List<FavoritePlace>,
    onSelectedFavoritePlace: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(items = listFavoritePlaces, key = { place -> place.id }) { place ->
            Card(
                shape = Shape.small,
                onClick = { onSelectedFavoritePlace() },
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