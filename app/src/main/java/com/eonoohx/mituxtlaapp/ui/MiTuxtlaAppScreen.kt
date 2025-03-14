package com.eonoohx.mituxtlaapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.screens.AboutScreen
import com.eonoohx.mituxtlaapp.ui.screens.ContentScreen
import com.eonoohx.mituxtlaapp.ui.screens.FeedbackScreen
import com.eonoohx.mituxtlaapp.ui.screens.PlaceInfoScreen
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

private enum class MiTuxtlaApp(@StringRes val title: Int) {
    MENU(title = R.string.app_name),
    CATEGORY(title = R.string.category),
    PLACE(title = R.string.place),
    FAVORITES(title = R.string.favorites),
    RESULTS(title = R.string.results),
    ABOUT(title = R.string.about),
    FEEDBACK(title = R.string.feedback)
}

@Composable
fun MiTuxtlaAppScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        MiTuxtlaApp.valueOf(backStackEntry?.destination?.route ?: MiTuxtlaApp.MENU.name)


    val list = mutableListOf<Pair<String, String>>()
    repeat(6) { list.add(Pair("", "")) }

    Scaffold(
        topBar = {
            MiTuxtlaTopAppBar(
                screenTitle = currentScreen.title,
                canNavigateUp = currentScreen.name != MiTuxtlaApp.MENU.name,
                goFavorites = { navController.navigate(route = MiTuxtlaApp.FAVORITES.name) },
                navigateUp = { navController.navigateUp() },
                onFeedbackSelected = { navController.navigate(route = MiTuxtlaApp.FEEDBACK.name) },
                onAboutSelected = { navController.navigate(route = MiTuxtlaApp.ABOUT.name) }
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = MiTuxtlaApp.MENU.name) {
                composable(route = MiTuxtlaApp.MENU.name) {
                    ContentScreen(list, isOnMenu = true, modifier = Modifier.fillMaxSize()) {
                        navController.navigate(route = MiTuxtlaApp.CATEGORY.name)
                    }
                }
                composable(route = MiTuxtlaApp.CATEGORY.name) {
                    ContentScreen(list, isOnMenu = false, modifier = Modifier.fillMaxSize()) {
                        navController.navigate(route = MiTuxtlaApp.PLACE.name)
                    }
                }
                composable(route = MiTuxtlaApp.FAVORITES.name) {
                    ContentScreen(list, isOnMenu = false, modifier = Modifier.fillMaxSize()) {
                        navController.navigate(route = MiTuxtlaApp.PLACE.name)
                    }
                }
                composable(route = MiTuxtlaApp.RESULTS.name) {
                    ContentScreen(list, isOnMenu = false, modifier = Modifier.fillMaxSize()) {
                        navController.navigate(route = MiTuxtlaApp.PLACE.name)
                    }
                }
                composable(route = MiTuxtlaApp.PLACE.name) {
                    PlaceInfoScreen(
                        place = {}, modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                composable(route = MiTuxtlaApp.FEEDBACK.name) {
                    FeedbackScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                composable(route = MiTuxtlaApp.ABOUT.name) {
                    AboutScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTuxtlaTopAppBar(
    @StringRes screenTitle: Int,
    canNavigateUp: Boolean,
    goFavorites: () -> Unit,
    navigateUp: () -> Unit,
    onFeedbackSelected: () -> Unit,
    onAboutSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = if (screenTitle != R.string.place) stringResource(screenTitle) else "",
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
                        tint = if (screenTitle != R.string.about && screenTitle != R.string.feedback
                            && screenTitle != R.string.place
                        )
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
                    onFeedbackOptionSelected = onFeedbackSelected,
                    onAboutOptionSelected = onAboutSelected
                )
            }
        },
        actions = {
            if (screenTitle != R.string.about && screenTitle != R.string.feedback
                && screenTitle != R.string.place
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                when (screenTitle) {
                    R.string.app_name -> {
                        IconButton(onClick = goFavorites) {
                            Icon(
                                imageVector = Icons.TwoTone.Favorite,
                                contentDescription = stringResource(R.string.favorite_places_button),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    R.string.favorites -> {
                        IconButton(onClick = { expandedMenu = !expandedMenu }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.filter_list),
                                contentDescription = stringResource(R.string.filter_places),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        PlaceFilterMenu(
                            expanded = expandedMenu,
                            onDismissRequest = { expandedMenu = false }
                        )
                    }
                }
            }
        },
        colors = when (screenTitle) {
            R.string.about, R.string.feedback -> TopAppBarDefaults.topAppBarColors(
                MaterialTheme.colorScheme.background
            )

            R.string.place -> TopAppBarDefaults.topAppBarColors(Color.Transparent)
            else -> TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
        },
        modifier = modifier
    )
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MiTuxtlaAppScreenPreview() {
    MiTuxtlaAppTheme {
        MiTuxtlaAppScreen()
    }
}