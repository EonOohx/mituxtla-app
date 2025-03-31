package com.eonoohx.mituxtlaapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.eonoohx.mituxtlaapp.network.PlaceInfo
import com.eonoohx.mituxtlaapp.network.PlaceLocation
import com.eonoohx.mituxtlaapp.ui.model.PlacesServiceUiState
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

private fun shareSheet(context: Context) {
    val share = Intent.createChooser(
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
            putExtra(Intent.EXTRA_TEXT, "Introducing place content")
            /**
            data = contentUri // Here you're passing a content URI to an image to be desplayed
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
             **/
        }, null
    )
    context.startActivity(share)
}

@Composable
fun PlaceInfoScreen(
    placesServiceUiState: PlacesServiceUiState<PlaceInfo>,
    modifier: Modifier = Modifier
) {
    when (placesServiceUiState) {
        is PlacesServiceUiState.Loading -> LoadingScreen()
        is PlacesServiceUiState.Success -> PlaceInfoCard(
            data = placesServiceUiState.data,
            modifier = modifier
        )

        is PlacesServiceUiState.Error -> ErrorScreen()
    }
}

@Composable
fun PlaceInfoCard(data: PlaceInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(data.photoUrl)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.img_loading),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.size_large))
                .background(color = Color.White)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(dimensionResource(R.dimen.padding_small))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            PlaceInfoHeader(placeName = data.name, saveAsFavoritePressed = {}, modifier = Modifier)
            Row(
                modifier = Modifier.wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.icon_large))
                        .padding(end = dimensionResource(R.dimen.padding_medium))
                )
                Column {
                    Text(text = data.address, style = MaterialTheme.typography.bodyLarge)
                    if (data.location != null) {
                        Text(
                            text = "Lat: ${data.location.lat}, Lng: ${data.location.lng}",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            if (data.phone != null) {
                Text(
                    text = "Phone: ${data.phone}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (data.website != null) {
                Text(
                    text = "Website: ${data.website}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun PlaceInfoHeader(
    placeName: String,
    saveAsFavoritePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = placeName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier.weight(1f)
        )
        Row {
            IconButton(onClick = saveAsFavoritePressed) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add as Favorite"
                )
            }
//            IconButton(onClick = { shareSheet(context = context) }
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Share,
//                    contentDescription = "Add as Favorite"
//                )
//            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlaceInfoScreenPreview() {
    val mockPlace = PlaceInfo(
        id = "mock_id_123",
        name = "Mock Place",
        rating = 4.5f, // Use a realistic rating
        photoUrl = "https://example.com/mock_photo.jpg", // Use a sample image URL
        address = "123 Fake Street, Test City",
        phone = "+1 234 567 890",
        isOpen = true,
        website = "https://example.com",
        location = PlaceLocation(37.7749, -122.4194),
        description = ""
    )
    MiTuxtlaAppTheme {
        PlaceInfoCard(data = mockPlace, modifier = Modifier.fillMaxSize())
    }
}