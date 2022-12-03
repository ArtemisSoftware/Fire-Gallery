package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.*
import com.artemissoftware.firegallery.screens.login.LogInRoute
import com.artemissoftware.firegallery.screens.register.RegisterRoute
import com.artemissoftware.firegallery.ui.FG_ScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profileNavigationGraph(
    navController: NavHostController,
    scaffoldState: FG_ScaffoldState
) {

    navigation(
        route = ProfileGraph.graph,
        startDestination = ProfileGraph.startDestination
    ) {

        RegisterRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        LogInRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

    }
}
