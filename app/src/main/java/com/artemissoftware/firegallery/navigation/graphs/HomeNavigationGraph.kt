package com.artemissoftware.firegallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.graphs.galleryNavigationGraph
import com.artemissoftware.firegallery.navigation.graphs.profileNavigationGraph
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.screens.favorites.FavoritesScreen
import com.artemissoftware.firegallery.screens.gallery.GalleryScreen
import com.artemissoftware.firegallery.screens.profile.ProfileScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeDestinations.Gallery.route
    ) {

        composable(route = HomeDestinations.Gallery.route) {
            GalleryScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                scaffoldState = scaffoldState
            )
        }

        composable(route = HomeDestinations.Favorites.route) {
            FavoritesScreen(
                onChangeCurrentPositionBottomBar = {
                    scaffoldState.changeCurrentPositionBottomBar(it.destination, navController = navController)
                },
                onNavigate = {
                    navController.navigate(it.route)
                },
                scaffoldState = scaffoldState
            )
        }

        composable(route = HomeDestinations.Profile.route) {
            ProfileScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                scaffoldState = scaffoldState
            )
        }

        galleryNavigationGraph(navController = navController, scaffoldState = scaffoldState)

        profileNavigationGraph(navController = navController, scaffoldState = scaffoldState)
    }
}


sealed class HomeDestinations(
    route: String,
    customArguments: List<CustomArguments> = emptyList()
) : BaseDestinations(route = route, customArguments = customArguments){

    object Gallery : HomeDestinations(route = "GALLERY")
    object Favorites : HomeDestinations(route = "FAVORITES")
    object Profile : HomeDestinations(route = "PROFILE")
}