package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.preference.AppTheme
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun ThemePreferenceScreen(
    isDarkTheme: Boolean,
    onSelectedTheme: (AppTheme) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDarkTheme by remember { mutableStateOf(isDarkTheme) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(dimensionResource(R.dimen.padding_medium)),
            shape = Shape.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.appearance_description),
                    style = MaterialTheme.typography.headlineMedium
                )

                ThemeOption(
                    text = stringResource(R.string.light_mode),
                    selectedDarkTheme = !selectedDarkTheme,
                    onClick = {
                        selectedDarkTheme = false
                        onSelectedTheme(AppTheme.LIGHT)
                    }
                )

                ThemeOption(
                    text = stringResource(R.string.dark_mode),
                    selectedDarkTheme = selectedDarkTheme,
                    onClick = {
                        selectedDarkTheme = true
                        onSelectedTheme(AppTheme.DARK)
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeOption(
    text: String,
    selectedDarkTheme: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()) {
        RadioButton(selected = selectedDarkTheme, onClick = onClick)
        Text(text = text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThemePreferencesScreenPreview() {
    MiTuxtlaAppTheme {
        ThemePreferenceScreen(onSelectedTheme = {}, isDarkTheme = false, onDismissRequest = {})
    }
}