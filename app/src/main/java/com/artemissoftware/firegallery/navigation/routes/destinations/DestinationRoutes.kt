package com.artemissoftware.firegallery.navigation.routes.destinations

class DestinationRoutes {

    object Root {

        val splash = Destination.Splash
    }

    object Home {

        const val GRAPH = "home_graph"
        val START_DESTINATION = Destination.Gallery.route

        val profile = Destination.Profile
        val gallery = Destination.Gallery
        val favorites = Destination.Favorites
        val tinderGallery = Destination.Tinder
    }

    object Profile {

        val register = Destination.RegisterUser
        val login = Destination.LogInUser
    }

    object Gallery {

        val pictures = Destination.Pictures
        val pictureDetail = Destination.PictureDetail
    }
}


