package com.eonoohx.mituxtlaapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.R

enum class PlaceProperty { CATEGORY, NAME, VIEWED }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePlaceTopBar(
    navigateUp: () -> Unit,
    orderBy: (PlaceProperty) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        actions = {
            IconButton(onClick = { expandedMenu = !expandedMenu }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter_list),
                    contentDescription = stringResource(R.string.filter_places),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false },
                modifier = Modifier.width(160.dp)
            ) {
                Text(
                    text = stringResource(R.string.order_by),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                )
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.by_name)) },
                    onClick = { orderBy(PlaceProperty.NAME) }
                )
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.by_viewed)) },
                    onClick = { orderBy(PlaceProperty.VIEWED) }
                )
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.by_category)) },
                    onClick = { orderBy(PlaceProperty.CATEGORY) }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
        modifier = modifier
    )
}