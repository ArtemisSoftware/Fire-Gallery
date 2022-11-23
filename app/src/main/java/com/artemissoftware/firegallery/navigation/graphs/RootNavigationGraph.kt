package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.screens.SplashScreen


//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun RootNavigationGraph(
//    navController: NavHostController,
//    scaffoldState: FGScaffoldState
//) {
//
//    AnimatedNavHost(
//        navController = navController,
//        route = Graph.ROOT,
//        startDestination = /*GalleryDestinations.Pictures.route*/RootDestinations.Splash.route
//    ) {
//
//        //--authenticationNavGraph(navController = navController)
//        //galleryNavigationGraph(navController = navController, scaffoldState = scaffoldState)
//        animatedComposable(route = RootDestinations.Splash.route) {
//            SplashScreen(
//                scaffoldState = scaffoldState,
//                onAnimationFinish = {
//                    navController.popBackStack()
//                    navController.navigate(RootDestinations.Home.route)
//
//                }
//            )
//        }
//
//        animatedComposable(
//            route = RootDestinations.Home.route,
//
//
//        ) {
//            HomeScreen(scaffoldState = scaffoldState)
//        }
//
//        galleryNavigationGraph(navController = navController, scaffoldState = scaffoldState, dplink = true)
//
//        //loloNavigationGraph(navController = navController, scaffoldState = scaffoldState)
////        navigation(
////            startDestination = RootDestinations.Details.route,
////            route = "nested_graph_route"
////        ) {
////
////        animatedComposable(
////            route = RootDestinations.Details.route,
////            arguments = listOf(navArgument(MY_ARG) { type = NavType.StringType }),
////            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
////        ) {
////            val arguments = it.arguments
////            arguments?.getString(MY_ARG)?.let { message ->
////                DetailsScreen(message = message)
////            }
////        }
////        }
//    }
//
//}


@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    //val startDestination = remember { mutableStateOf(RootDestinations.Splash.route) }
    val context = LocalContext.current
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = RootDestinations.Splash.route
    ) {

        composable(route = RootDestinations.Splash.route) {
            SplashScreen(
                scaffoldState = scaffoldState,
                onFinishAndStartActivity = {
                             scaffoldState.startNextAndFinish(it.activity, context )
                },
                onNavigatePopUpTo = {
                    navController.navigate(it.destinationRoute) {
                        popUpTo(it.currentRoute) {
                            inclusive = true
                        }
                    }
                },

            )
        }

//        composable(route = RootDestinations.Home.route) {
//            HomeScreen(scaffoldState = scaffoldState)
//        }


        //deeplinkNavigationGraph(navController = navController, scaffoldState = scaffoldState, startDestination)

    }

}


sealed class RootDestinations(route: String) : BaseDestinations(route = route){
    object Home : RootDestinations(route = "HOME")
    object Splash : RootDestinations(route = "SPLASH")

}