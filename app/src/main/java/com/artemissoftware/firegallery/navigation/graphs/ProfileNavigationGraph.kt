package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.*
import com.artemissoftware.firegallery.screens.login.LogInRoute
import com.artemissoftware.firegallery.screens.register.RegisterRoute

@OptIn(ExperimentalAnimationApi::class)
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
