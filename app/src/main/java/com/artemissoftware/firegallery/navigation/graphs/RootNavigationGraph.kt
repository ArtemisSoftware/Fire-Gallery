package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.RootGraph
import com.artemissoftware.firegallery.screens.splash.SplashRoute
import com.artemissoftware.firegallery.ui.FGScaffoldState
import com.google.accompanist.navigation.animation.AnimatedNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    AnimatedNavHost(
        navController = navController,
        route = RootGraph.graph,
        startDestination = RootGraph.startDestination
    ) {

        SplashRoute.composable(
            navGraphBuilder = this,
            scaffoldState = scaffoldState,
            navController = navController
        )
    }

}
