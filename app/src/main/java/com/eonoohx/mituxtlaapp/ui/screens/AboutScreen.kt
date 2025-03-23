package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Image(
            painter = painterResource(R.drawable.img_loading),
            contentDescription = "",
            modifier = Modifier
                .size(dimensionResource(R.dimen.size_medium))
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Text of the about page", modifier = Modifier.fillMaxWidth())
        Text(text = "Version: 1.0. Released on March 20th 2025")
        Text(text = "Contact", style = MaterialTheme.typography.headlineMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Image(
                painter = painterResource(R.drawable.ic_eonoohx),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_large))
            )
            Text(text = stringResource(R.string.developer))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_github_light),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_large))
            )
            Text(text = stringResource(R.string.github))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_large))
            )
            Text(text = stringResource(R.string.email))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AboutScreenPreview() {
    MiTuxtlaAppTheme {
        AboutScreen(modifier = Modifier.fillMaxSize())
    }
}

