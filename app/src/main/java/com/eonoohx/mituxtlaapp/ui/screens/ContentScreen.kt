package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme
import com.eonoohx.mituxtlaapp.ui.theme.Shape

@Composable
fun ContentScreen(
    contentList: List<Pair<String, String>>,
    isOnMenu: Boolean,
    modifier: Modifier = Modifier,
    onSelectOptionClicked: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(contentList) { element ->
            Card(shape = Shape.small, onClick = onSelectOptionClicked) {
                if (isOnMenu) MenuContentScreen(
                    text = "Content Menu",
                    modifier = Modifier.padding(1.dp)
                )
                else CategoryContentScreen(
                    text = "Content Category",
                    modifier = Modifier.padding(1.dp)
                )
            }
        }
    }
}

@Composable
fun MenuContentScreen(text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ContentText(
            text = text,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        )
        Image(
            painter = painterResource(R.drawable.loading),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .background(Color.White)
        )
    }
}

@Composable
fun CategoryContentScreen(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(R.drawable.loading),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.White)
        )
        ContentText(
            text = text,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f)),
            isOnMenu = false
        )
    }
}

@Composable
fun ContentText(text: String, modifier: Modifier = Modifier, isOnMenu: Boolean = true) {
    Box(
        modifier = modifier.height(60.dp),
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
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MenuContentScreenPreview() {
    val list = mutableListOf<Pair<String, String>>()
    repeat(6) { list.add(Pair("", "")) }
    MiTuxtlaAppTheme {
        ContentScreen(
            list,
            isOnMenu = true,
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
        ContentScreen(
            list,
            isOnMenu = false,
            onSelectOptionClicked = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}