package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.RootGraph
import com.artemissoftware.firegallery.screens.splash.SplashRoute
import com.artemissoftware.firegallery.ui.FG_ScaffoldState


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
    scaffoldState: FG_ScaffoldState
) {

    val startDestination = remember { mutableStateOf(RootDestinations.Splash.route) }

    NavHost(
        navController = navController,
        route = RootGraph.graph,
        startDestination = RootGraph.startDestination
    ) {

        SplashRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)


//        composable(route = RootDestinations.Splash.route) {
//            SplashScreen(
//                scaffoldState = scaffoldState,
//                onFinishAndStartActivity = {
//                    val intent = Intent(context, it.activity)
//
//                    context.startActivity(scaffoldState.updateIntent(intent))
//                    (context as? Activity)?.finish()
//                },
//                onNavigatePopUpTo = {
//                    navController.navigate(it.destinationRoute) {
//                        popUpTo(it.currentRoute) {
//                            inclusive = true
//                        }
//                    }
//                },
//
//                )
//        }


//        composable(route = RootDestinations.Splash.route) {
//            SplashScreen(
//                scaffoldState = scaffoldState,
//                onNavigatePopUpTo = {
//                    navController.navigate(it.destinationRoute) {
//                        popUpTo(it.currentRoute) {
//                            inclusive = true
//                        }
//                    }
//                },
//
//            )
//        }

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