package com.artemissoftware.firegallery.navigation.routes.destinations

import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.graphs.RootDestinations
import com.artemissoftware.firegallery.navigation.navtypes.GalleryUINavType

sealed class Destination(
    route: String,
    customArguments: List<CustomArguments> = emptyList(),
    baseDeepLink: String = NavigationArguments.FIRE_GALLERY_URI
) : BaseDestinations(route = route, customArguments = customArguments, baseDeepLink = baseDeepLink){

    object Profile : Destination(route = "PROFILE")
    object RegisterUser : Destination(route = "REGISTER_USER")
    object LogInUser : Destination(route = "LOG_IN_USER", customArguments = listOf(CustomArguments(NavigationArguments.REDIRECT_ROUTE, nullable = true)))
    object Pictures : Destination(route = "PICTURES", listOf(CustomArguments(key = NavigationArguments.GALLERY_ID, type = GalleryUINavType())))
    object Gallery : Destination(route = "GALLERY")
    object PictureDetail : Destination(route = "PICTURE_DETAIL", customArguments = listOf(CustomArguments(NavigationArguments.PICTURE_ID)))
    object Favorites : Destination(route = "FAVORITES")
    object Tinder : Destination(route = "TINDER", customArguments = listOf(CustomArguments(NavigationArguments.SEASON)))
    object Splash : Destination(route = "SPLASH")

}