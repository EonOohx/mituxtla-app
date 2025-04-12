package com.eonoohx.mituxtlaapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eonoohx.mituxtlaapp.R

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    @StringRes message: Int = R.string.loading_message,
    @StringRes contentDescription: Int = R.string.loading_message
) =
    StateScreen(
        stateImage = R.drawable.img_loading,
        message = message,
        contentDescription = contentDescription,
        modifier = modifier
    )

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    @StringRes message: Int = R.string.failed_connection_description,
    @StringRes contentDescription: Int = R.string.failed_connection_description
) =
    StateScreen(
        stateImage = R.drawable.ic_cloud_off,
        message = message,
        contentDescription = contentDescription,
        modifier = modifier
    )