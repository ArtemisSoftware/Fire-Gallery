package com.artemissoftware.firegallery.navigation.routes.destinations

class DestinationRoutes {

    object Home {

        val profile = Destination.Profile
        val gallery = Destination.Gallery
        val favorites = Destination.Favorites
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


