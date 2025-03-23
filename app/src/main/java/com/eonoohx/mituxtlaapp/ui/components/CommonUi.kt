package com.eonoohx.mituxtlaapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
fun StateScreen(
    @DrawableRes stateImage: Int,
    @StringRes message: Int,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(stateImage),
            contentDescription = stringResource(contentDescription),
            modifier.size(
                dimensionResource(R.dimen.size_large)
            )
        )
        Text(text = stringResource(message))
    }
}