package com.artemissoftware.firegallery.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.GalleryGraph
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailRoute
import com.artemissoftware.firegallery.screens.pictures.PicturesRoute
import com.artemissoftware.firegallery.ui.FGScaffoldState

fun NavGraphBuilder.galleryNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    navigation(
        route = GalleryGraph.graph,
        startDestination = GalleryGraph.startDestination
    ) {


        PicturesRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        PictureDetailRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)
    }
}

