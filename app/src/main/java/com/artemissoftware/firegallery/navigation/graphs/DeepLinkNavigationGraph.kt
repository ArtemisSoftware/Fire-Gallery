package com.artemissoftware.firegallery.navigation.graphs

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.models.Graph.DEEP_LINK
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailScreen

fun NavGraphBuilder.deeplinkNavigationGraph(
    navController: NavHostController,
    scaffoldState: FGScaffoldState,
    startDestination: MutableState<String>
) {
    navigation(
        startDestination = DeepLinkDestinations.PictureDetail.route,
        route = DEEP_LINK
    ) {

        composable(
            route = DeepLinkDestinations.PictureDetail.route,
            arguments = DeepLinkDestinations.PictureDetail.arguments,
            deepLinks = DeepLinkDestinations.PictureDetail.deepLink
        ) {

            startDestination.value = RootDestinations.Home.route
            PictureDetailScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState
            )
        }

    }
}


sealed class DeepLinkDestinations(
    route: String,
    customArguments: List<CustomArguments> = emptyList(),
    baseDeepLink: String = NavigationArguments.FIRE_GALLERY_URI
) : BaseDestinations(route = route, customArguments = customArguments, baseDeepLink = baseDeepLink){

    object PictureDetail : DeepLinkDestinations(
        route = "PICTURE_DETAIL",
        customArguments = listOf(
            CustomArguments(
                NavigationArguments.PICTURE_ID)
            )
    )
}