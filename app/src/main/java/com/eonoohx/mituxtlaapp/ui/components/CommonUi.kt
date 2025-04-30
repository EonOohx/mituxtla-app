package com.eonoohx.mituxtlaapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.eonoohx.mituxtlaapp.R

@Composable
fun ContentText(text: String, modifier: Modifier = Modifier, isOnMenu: Boolean = true) {
    Box(
        modifier = modifier.height(dimensionResource(R.dimen.content_card_size)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
            color = if (isOnMenu) MaterialTheme.colorScheme.onSecondary
            else Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun ApiStateScreen(
    @DrawableRes stateImage: Int,
    @StringRes message: Int,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        Image(
            painter = painterResource(stateImage),
            contentDescription = stringResource(contentDescription),
            modifier.size(
                dimensionResource(R.dimen.size_large)
            )
        )
        Text(text = stringResource(message), textAlign = TextAlign.Center)
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

@Preview(showBackground = true)
@Composable
fun ApiStateScreenPreview() {
    ApiStateScreen(
        stateImage = R.drawable.ic_broken_image,
        contentDescription = R.string.network_error,
        message = R.string.network_error
    )
}