package com.artemissoftware.firegallery.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.ProfileGraph
import com.artemissoftware.firegallery.screens.login.LogInRoute
import com.artemissoftware.firegallery.screens.register.RegisterRoute
import com.artemissoftware.firegallery.ui.FGScaffoldState

fun NavGraphBuilder.profileNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    navigation(
        route = ProfileGraph.graph,
        startDestination = ProfileGraph.startDestination
    ) {

        RegisterRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

        LogInRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

    }
}
