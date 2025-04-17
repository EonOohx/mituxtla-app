package com.eonoohx.mituxtlaapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eonoohx.mituxtlaapp.R
import com.eonoohx.mituxtlaapp.data.preference.AppTheme
import com.eonoohx.mituxtlaapp.ui.components.FavoritePlaceTopBar
import com.eonoohx.mituxtlaapp.ui.components.MiTuxtlaTopAppBar
import com.eonoohx.mituxtlaapp.ui.components.PlaceInfoTopBar
import com.eonoohx.mituxtlaapp.ui.model.MiTuxtlaViewModel
import com.eonoohx.mituxtlaapp.ui.screens.AboutScreen
import com.eonoohx.mituxtlaapp.ui.screens.FavoritePlaceInfoScreen
import com.eonoohx.mituxtlaapp.ui.screens.FavoritePlaceScreen
import com.eonoohx.mituxtlaapp.ui.screens.MainScreen
import com.eonoohx.mituxtlaapp.ui.screens.PlaceInfoScreen
import com.eonoohx.mituxtlaapp.ui.screens.PlacesScreen
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

private enum class MiTuxtlaApp(@StringRes val title: Int) {
    MENU(title = R.string.app_name),
    CATEGORY(title = R.string.category),
    PLACE(title = R.string.place),
    FAVORITES(title = R.string.favorites),
    ABOUT(title = R.string.about),
}

@Composable
fun MiTuxtlaAppScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val miTuxtlaViewModel: MiTuxtlaViewModel = viewModel(factory = MiTuxtlaViewModel.Factory)

    val currentScreen = MiTuxtlaApp.valueOf(
        backStackEntry?.destination?.route
            ?: MiTuxtlaApp.MENU.name
    )

    val placeUiState by miTuxtlaViewModel.miTuxtlaUiState.collectAsState()
    val themeUiState by miTuxtlaViewModel.theme.collectAsState()

    val isDarkTheme = themeUiState != AppTheme.LIGHT

    MiTuxtlaAppTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                when (currentScreen.name) {
                    MiTuxtlaApp.FAVORITES.name -> {
                        FavoritePlaceTopBar(
                            navigateUp = { navController.navigateUp() },
                            orderBy = { property -> miTuxtlaViewModel.onSortRequest(property) }
                        )
                    }

                    MiTuxtlaApp.PLACE.name -> {
                        PlaceInfoTopBar(navigateUp = { navController.navigateUp() })
                    }

                    else -> {
                        MiTuxtlaTopAppBar(
                            screenTitle = if (currentScreen != MiTuxtlaApp.CATEGORY)
                                currentScreen.title else placeUiState.currentCategory
                                ?: R.string.category,
                            canNavigateUp = currentScreen.name != MiTuxtlaApp.MENU.name,
                            navigateUp = { navController.navigateUp() },
                            onAboutSelected = { navController.navigate(route = MiTuxtlaApp.ABOUT.name) },
                            onFavoritesSelected = {
                                miTuxtlaViewModel.loadFavoritePlaces()
                                navController.navigate(route = MiTuxtlaApp.FAVORITES.name)
                            },
                            onSelectedTheme = { appTheme -> miTuxtlaViewModel.selectTheme(appTheme) },
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
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
                        val context = LocalContext.current
                        MainScreen(
                            listOfCategories = placeUiState.categories,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            miTuxtlaViewModel.setCurrentCategory(it)
                            miTuxtlaViewModel.getApiPlacesList(context.resources.getString(it))
                            navController.navigate(MiTuxtlaApp.CATEGORY.name)
                        }
                    }

                    composable(route = MiTuxtlaApp.CATEGORY.name) {
                        val placesServiceUiState = miTuxtlaViewModel.listPlacesUiState

                        PlacesScreen(
                            placeServiceUiState = placesServiceUiState,
                            modifier = Modifier.fillMaxSize()
                        ) { placeId ->
                            miTuxtlaViewModel.getApiPlaceInfo(placeId = placeId)
                            miTuxtlaViewModel.viewFavoritePlaceScreen(viewing = false)
                            navController.navigate(route = MiTuxtlaApp.PLACE.name)
                        }
                    }

                    composable(route = MiTuxtlaApp.FAVORITES.name) {
                        val favoritePlaceUiState =
                            miTuxtlaViewModel.favoritePlaceUiState.collectAsState()
                        FavoritePlaceScreen(favoritePlaceUiState = favoritePlaceUiState) { placeId ->
                            miTuxtlaViewModel.loadFavoritePlace(placeId)
                            miTuxtlaViewModel.viewFavoritePlaceScreen(viewing = true)
                            navController.navigate(route = MiTuxtlaApp.PLACE.name)
                        }
                    }

                    composable(route = MiTuxtlaApp.PLACE.name) {
                        val placeInfoUiState = miTuxtlaViewModel.placeInfoUiState
                        Surface {
                            if (!placeUiState.forViewFavoritePlace) {
                                PlaceInfoScreen(
                                    placeServiceUiState = placeInfoUiState,
                                    miTuxtlaUiState = placeUiState,
                                    currentCategory = placeUiState.currentCategory!!,
                                    onDeleteAsFavorite = { placeId ->
                                        miTuxtlaViewModel.deleteFavoritePlace(placeId)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(dimensionResource(R.dimen.padding_medium))
                                ) { place, category ->
                                    miTuxtlaViewModel.savePlace(place, category)
                                }
                            } else {
                                val favoritePlaceDetailsUiState by miTuxtlaViewModel.favoritePlaceUiState.collectAsState()

                                FavoritePlaceInfoScreen(
                                    place = favoritePlaceDetailsUiState.favoritePlaceDetails,
                                    miTuxtlaUiState = placeUiState,
                                    onDeleteAsFavorite = { placeId ->
                                        miTuxtlaViewModel.deleteFavoritePlace(placeId)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(dimensionResource(R.dimen.padding_medium))
                                ) { place, category ->
                                    miTuxtlaViewModel.savePlace(place, category)
                                }
                            }
                        }
                    }

                    composable(route = MiTuxtlaApp.ABOUT.name) {
                        Surface {
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
    }
}