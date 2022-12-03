package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.navtypes.GalleryUINavType
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes.*
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailRoute
import com.artemissoftware.firegallery.screens.pictures.PicturesRoute
import com.artemissoftware.firegallery.ui.FG_ScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.galleryNavigationGraph(
    navController: NavHostController,
    scaffoldState: FG_ScaffoldState
) {


    navigation(
        route = GalleryGraph.graph,
        startDestination = GalleryGraph.startDestination
    ) {


//        composable(
//            route = GalleryDestinations.Details.route,
//            //arguments = GalleryDestinations.Pictures.arguments,
//            //--deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
//        ){
//            DetailsScreen(message = "message")
//        }


//        composable(
//            route = GalleryDestinations.Details.fullRoute,
//            arguments = listOf(navArgument(MY_ARG) { type = NavType.StringType }),
//            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
//        ) {
//            val arguments = it.arguments
//            arguments?.getString(MY_ARG)?.let { message ->
//                DetailsScreen(message = message)
//            }
//        }

//        animatedComposable(
//            route = GalleryDestinations.Details.route,
//            arguments = listOf(navArgument(MY_ARG) { type = NavType.StringType }),
//            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
//        ) {
//            val arguments = it.arguments
//            arguments?.getString(MY_ARG)?.let { message ->
//                DetailsScreen(message = message)
//            }
//        }



        //TODO: verificar se precisa de ser full route
        PicturesRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)


//        composable(
//            route = GalleryDestinations.Pictures.fullRoute,
//            arguments = GalleryDestinations.Pictures.arguments,
//        ){
//
//
//            PicturesScreen(
//                onPopBackStack = {
//                    navController.popBackStack()
//                },
//                scaffoldState = scaffoldState,
//                onNavigate = {
//                    navController.navigate(it.route)
//                }
//            )
//        }

        //TODO: verificar os argumentos
        PictureDetailRoute.composable(navGraphBuilder = this, scaffoldState = scaffoldState, navController = navController)

//        composable(
//            route = GalleryDestinations.PictureDetail.fullRoute,
//            arguments = GalleryDestinations.PictureDetail.arguments,
//            deepLinks = GalleryDestinations.PictureDetail.deepLink
//        ) {
//            PictureDetailScreen(
//                onPopBackStack = {
//                    navController.popBackStack()
//                },
//                scaffoldState = scaffoldState
//            )
//        }


//        composable(
//            route = GalleryDestinations.PictureDetail.fullRoute,
//            arguments = GalleryDestinations.Pictures.arguments
//        ) {
//            PictureDetailScreen(
//                onPopBackStack = {
//                    navController.popBackStack()
//                },
//                scaffoldState = scaffoldState
//            )
//        }
    }
}

sealed class GalleryDestinations(
    route: String,
    customArguments: List<CustomArguments> = emptyList(),
    baseDeepLink: String = NavigationArguments.FIRE_GALLERY_URI
) : BaseDestinations(route = route, customArguments = customArguments, baseDeepLink = baseDeepLink){

    object Pictures : GalleryDestinations(route = "PICTURES", listOf(CustomArguments(key = NavigationArguments.GALLERY_ID, type = GalleryUINavType())))
    object PictureDetail : GalleryDestinations(route = "PICTURE_DETAIL", listOf(CustomArguments(NavigationArguments.PICTURE_ID)))
}

