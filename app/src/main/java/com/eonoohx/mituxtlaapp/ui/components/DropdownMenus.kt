package com.eonoohx.mituxtlaapp.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.R

@Composable
fun MiTuxtlaAppMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onFeedbackOptionSelected: () -> Unit,
    onAboutOptionSelected: () -> Unit,
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
            onClick = {},
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.feedback_button)) },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_feedback),
                    contentDescription = stringResource(R.string.feedback_description)
                )
            },
            onClick = onFeedbackOptionSelected,
        )
        HorizontalDivider()
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

@Composable
fun PlaceFilterMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.width(160.dp)
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.category_option)) },
            onClick = {}
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.viewed_option)) },
            onClick = {}
        )
    }
}