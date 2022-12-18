package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.HomeGraph
import com.artemissoftware.firegallery.screens.favorites.FavoritesRoute
import com.artemissoftware.firegallery.screens.gallery.GalleryRoute
import com.artemissoftware.firegallery.screens.profile.ProfileRoute
import com.artemissoftware.firegallery.screens.tindergallery.TinderGalleryRoute
import com.artemissoftware.firegallery.ui.FGScaffoldState
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    AnimatedNavHost(
        navController = navController,
        route = HomeGraph.graph,
        startDestination = HomeGraph.startDestination
    ) {

        GalleryRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        FavoritesRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        TinderGalleryRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        ProfileRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)



        galleryNavigationGraph(navController = navController, scaffoldState = scaffoldState)

        profileNavigationGraph(navController = navController, scaffoldState = scaffoldState)
    }
}

