package com.eonoohx.mituxtlaapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.ui.components.MiTuxtlaTopAppBar
import com.eonoohx.mituxtlaapp.ui.components.PlaceInfoTopBar
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
            if (currentScreen.name != MiTuxtlaApp.PLACE.name) {
                MiTuxtlaTopAppBar(
                    screenTitle = currentScreen.title,
                    canNavigateUp = currentScreen.name != MiTuxtlaApp.MENU.name,
                    goFavorites = { navController.navigate(route = MiTuxtlaApp.FAVORITES.name) },
                    navigateUp = { navController.navigateUp() },
                    onFeedbackSelected = { navController.navigate(route = MiTuxtlaApp.FEEDBACK.name) },
                    onAboutSelected = { navController.navigate(route = MiTuxtlaApp.ABOUT.name) }
                )
            } else PlaceInfoTopBar(navigateUp = { navController.navigateUp() })
        },
    ) { innerPadding ->
        Surface(
            modifier = if (currentScreen.name != MiTuxtlaApp.PLACE.name) Modifier.padding(
                innerPadding
            ) else Modifier.safeDrawingPadding(),
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
                        place = {},
                        modifier = Modifier
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

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MiTuxtlaAppScreenPreview() {
    MiTuxtlaAppTheme {
        MiTuxtlaAppScreen()
    }
}