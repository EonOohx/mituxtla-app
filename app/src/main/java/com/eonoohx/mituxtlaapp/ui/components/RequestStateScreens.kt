package com.eonoohx.mituxtlaapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.utils.RepositoryErrorType

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    @StringRes message: Int = R.string.loading_message,
    @StringRes contentDescription: Int = R.string.loading_message
) =
    RepositoryStateScreen(
        stateImage = R.drawable.img_loading,
        message = message,
        contentDescription = contentDescription,
        modifier = modifier
    )

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    error: RepositoryErrorType,
) {
    RepositoryStateScreen(
        stateImage = R.drawable.ic_cloud_off,
        message = error.errorMessage,
        contentDescription = error.contentDescription,
        modifier = modifier
    )
}
