package com.artemissoftware.firegallery.navigation

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.screens.HomeScreen
import com.artemissoftware.firegallery.screens.SplashScreen
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailScreen


const val MY_ARG = "message"
const val MY_LOLO = "lolo"


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

    var startDestination = remember { mutableStateOf(RootDestinations.Splash.route) }

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination.value//RootDestinations.Splash.route
    ) {

        //--authenticationNavGraph(navController = navController)
        //galleryNavigationGraph(navController = navController, scaffoldState = scaffoldState)
        composable(route = RootDestinations.Splash.route) {
            SplashScreen(
                scaffoldState = scaffoldState,
                onAnimationFinish = {
                    navController.popBackStack()
                    navController.navigate(RootDestinations.Home.route)

                }
            )
        }

        composable(
            route = RootDestinations.Home.route,


            ) {
            HomeScreen(scaffoldState = scaffoldState)
        }



        loloNavigationGraph(navController = navController, scaffoldState = scaffoldState, startDestination)

    }

}


sealed class RootDestinations(val route: String) : BaseDestinations(route = route){
    object Home : RootDestinations(route = "HOME")
    object Splash : RootDestinations(route = "SPLASH")

}