package com.eonoohx.mituxtlaapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.local.PlaceCategories
import com.eonoohx.mituxtlaapp.ui.components.ContentText
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onSelectOptionClicked: (String) -> Unit,
) {
    val listOfCategories = PlaceCategories.listOfCategories
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
    ) {
        items(listOfCategories) { category ->
            val categoryName = stringResource(category.title)
            Card(
                shape = Shape.small,
                onClick = { onSelectOptionClicked(categoryName) }) {
                MenuContentScreen(
                    text = stringResource(category.title),
                    image = category.image,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_tiny))
                )
            }
        }
    }
}

@Composable
fun MenuContentScreen(text: String, @DrawableRes image: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ContentText(
            text = text,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        )
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .background(Color.White)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun MenuContentScreenPreview() {
    val list = mutableListOf<Pair<String, String>>()
    repeat(6) { list.add(Pair("", "")) }
    MiTuxtlaAppTheme {
        MainScreen(
            onSelectOptionClicked = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryContentScreenPreview() {
    val list = mutableListOf<Pair<String, String>>()
    repeat(6) { list.add(Pair("", "")) }
    MiTuxtlaAppTheme {
        MainScreen(
            onSelectOptionClicked = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}