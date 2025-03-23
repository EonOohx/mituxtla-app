package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.components.StateScreen

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) =
    StateScreen(
        stateImage = R.drawable.ic_loading,
        message = R.string.loading_message,
        contentDescription = R.string.loading_message,
        modifier = modifier
    )

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) =
    StateScreen(
        stateImage = R.drawable.ic_cloud_off,
        message = R.string.failed_connection_description,
        contentDescription = R.string.failed_connection_description,
        modifier = modifier
    )