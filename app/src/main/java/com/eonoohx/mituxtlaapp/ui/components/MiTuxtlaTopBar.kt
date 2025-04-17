package com.eonoohx.mituxtlaapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.preference.AppTheme
import com.eonoohx.mituxtlaapp.ui.screens.ThemePreferenceScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTuxtlaTopAppBar(
    @StringRes screenTitle: Int,
    canNavigateUp: Boolean,
    navigateUp: () -> Unit,
    onAboutSelected: () -> Unit,
    onFavoritesSelected: () -> Unit,
    onSelectedTheme: (AppTheme) -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    var expandedMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ThemePreferenceScreen(
            onSelectedTheme = onSelectedTheme,
            isDarkTheme = isDarkTheme,
            onDismissRequest = { showDialog = false })
    }

    TopAppBar(
        title = {
            Text(
                text = stringResource(screenTitle),
                style = MaterialTheme.typography.headlineLarge,
                color = if (screenTitle != R.string.about && screenTitle != R.string.feedback)
                    MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = if (screenTitle != R.string.about && screenTitle != R.string.feedback)
                            MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                IconButton(
                    onClick = { expandedMenu = !expandedMenu },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.menu_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                MiTuxtlaAppMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false },
                    onAboutOptionSelected = onAboutSelected,
                    onChangeTheme = { showDialog = true }
                )
            }
        },
        actions = {
            if (screenTitle != R.string.about && screenTitle != R.string.place) {
                IconButton(onClick = onFavoritesSelected) {
                    Icon(
                        imageVector = Icons.TwoTone.Favorite,
                        contentDescription = stringResource(R.string.favorite_places_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = if (screenTitle == R.string.about)
            TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        else TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
        modifier = modifier
    )
}

@Composable
fun MiTuxtlaAppMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onAboutOptionSelected: () -> Unit,
    onChangeTheme: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest, modifier = modifier) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.appearance_button)) },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_routine),
                    contentDescription = stringResource(R.string.appearance_description)
                )
            },
            onClick = onChangeTheme,
        )
        HorizontalDivider(
            modifier = Modifier.border(
                width = dimensionResource(R.dimen.padding_medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.about)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.about)
                )
            },
            onClick = onAboutOptionSelected,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceInfoTopBar(navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        navigationIcon = {
            FloatingActionButton(
                onClick = navigateUp,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        modifier = modifier
    )
}