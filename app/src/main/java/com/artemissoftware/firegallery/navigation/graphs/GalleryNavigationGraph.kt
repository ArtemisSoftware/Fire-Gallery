package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.navigation.animatedComposable
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.DetailsScreen
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.NavigationArguments.ARTEMIS_SOFTWARE_URI
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.navigation.navtypes.GalleryUINavType
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailScreen
import com.artemissoftware.firegallery.screens.pictures.PicturesScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.galleryNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState,
    dplink: Boolean = false
) {


    navigation(
        route = Graph.GALLERY,
        startDestination = GalleryDestinations.Pictures.route
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



        composable(
            route = GalleryDestinations.Pictures.fullRoute,
            arguments = GalleryDestinations.Pictures.arguments,
        ){

            PicturesScreen(navController = navController, scaffoldState = scaffoldState)
        }

        composable(
            route = GalleryDestinations.PictureDetail.fullRoute,
            arguments = GalleryDestinations.Pictures.arguments
        ) {
            PictureDetailScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState
            )
        }
    }
}

sealed class GalleryDestinations(
    route: String,
    customArguments: List<CustomArguments> = emptyList()
) : BaseDestinations(route = route, customArguments = customArguments){

    object Pictures : GalleryDestinations(route = "PICTURES", listOf(CustomArguments(key = NavigationArguments.GALLERY_ID, type = GalleryUINavType())))
    object PictureDetail : GalleryDestinations(route = "PICTURE_DETAIL", listOf(CustomArguments(NavigationArguments.PICTURE_ID)))
}

