package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.screens.login.LogInScreen
import com.artemissoftware.firegallery.screens.register.RegisterScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profileNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {


    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileDestinations.RegisterUser.route
    ) {

        composable(
            route = ProfileDestinations.RegisterUser.route,
            arguments = ProfileDestinations.RegisterUser.arguments,
        ){

            RegisterScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = ProfileDestinations.LogInUser.route,
            arguments = ProfileDestinations.LogInUser.arguments,
        ){

            LogInScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState
            )
        }
    }
}

sealed class ProfileDestinations(
    route: String,
    customArguments: List<CustomArguments> = emptyList()
) : BaseDestinations(route = route, customArguments = customArguments){

    object RegisterUser : ProfileDestinations(route = "REGISTER_USER")
    object LogInUser : ProfileDestinations(route = "LOG_IN_USER")
}