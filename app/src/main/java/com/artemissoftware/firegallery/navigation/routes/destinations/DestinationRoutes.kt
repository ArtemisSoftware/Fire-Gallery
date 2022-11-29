package com.artemissoftware.firegallery.navigation.routes.destinations

import com.artemissoftware.firegallery.navigation.routes.NavigationGraph

class DestinationRoutes {

    object RootGraph : NavigationGraph {

        override val graph = "root_graph"
        override val startDestination = Destination.Splash.route

        val splash = Destination.Splash
    }

    object HomeGraph : NavigationGraph {

        override val graph = "home_graph"
        override val startDestination = Destination.Gallery.route

        val profile = Destination.Profile
        val gallery = Destination.Gallery
        val favorites = Destination.Favorites
        val tinderGallery = Destination.Tinder
    }

    object ProfileGraph : NavigationGraph {

        override val graph = "profile_graph"
        override val startDestination = Destination.RegisterUser.route

        val register = Destination.RegisterUser
        val login = Destination.LogInUser
    }

    object GalleryGraph : NavigationGraph {

        override val graph = "gallery_graph"
        override val startDestination = Destination.RegisterUser.route

        val pictures = Destination.Pictures
        val pictureDetail = Destination.PictureDetail
    }
}


