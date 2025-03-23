package com.eonoohx.mituxtlaapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.Share
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
import com.eonoohx.mituxtlaapp.R
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
fun PlaceInfoScreen(place: Any, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_loading),
            contentScale = ContentScale.Crop,
            contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.size_large))
                .background(color = Color.White))
        PlaceInfoField(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun PlaceInfoField(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    ) {
        PlaceInfoHeader(placeName = "Place Name", modifier = modifier)
        Row(
            modifier = modifier.wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.icon_large))
                    .padding(end = dimensionResource(R.dimen.padding_medium))
            )
            Text(text = "Place Address", style = MaterialTheme.typography.bodyLarge)
        }
        Text(
            text = "Text Description",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier
        )
    }
}

@Composable
fun PlaceInfoHeader(placeName: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = placeName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier.weight(1f)
        )
        Row {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add as Favorite"
                )
            }
            IconButton(onClick = { shareSheet(context = context) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Add as Favorite"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlaceInfoScreenPreview() {
    MiTuxtlaAppTheme {
        PlaceInfoScreen(place = {}, modifier = Modifier.fillMaxSize())
    }
}