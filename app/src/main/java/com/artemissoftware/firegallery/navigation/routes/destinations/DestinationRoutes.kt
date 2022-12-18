package com.artemissoftware.firegallery.navigation.routes.destinations

import android.net.Uri
import androidx.navigation.NamedNavArgument
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.NavigationArguments.FIRE_GALLERY_URI
import com.artemissoftware.firegallery.navigation.routes.NavigationGraph
import java.util.*

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

        fun getRoutes() = listOf(Destination.Profile, Destination.Gallery, Destination.Favorites, Destination.Tinder)
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


    private fun getDestination(destinations: List<Destination>, path: String) : Destination?{

        val formattedPath = path.lowercase(Locale.ROOT)
        return destinations.find { formattedPath.contains(it.route.lowercase(Locale.ROOT)) || argumentCheck(it, path) }
    }

    private fun argumentCheck(dest: Destination, path: String): Boolean{

        if(dest.arguments.isEmpty()) return false

        dest.arguments.forEach {
            if(!path.contains(it.name)){
                return false
            }
        }

        return true
    }

    private fun getUriArguments(uri: Uri, path: String, destinationArguments: List<NamedNavArgument>) : List<String>{

        var arguments = mutableListOf<String>()

        uri.getQueryParameter(NavigationArguments.SEASON)?.let { argument-> arguments.add(argument) }

        if(arguments.isEmpty()){

            val tempUri = Uri.parse(FIRE_GALLERY_URI + "/temp?" + path.removePrefix("/"))

            destinationArguments.forEach { arg->
                tempUri.getQueryParameter(arg.name)?.let { argument-> arguments.add(argument) }
            }
        }

        return arguments
    }

    private fun getHomeGraphDestination(uri: Uri): Pair<Destination, List<String>>? {

        uri.path?.let { path->

            getDestination(HomeGraph.getRoutes(), path)?.let { destination->

                return when (destination) {

                    is Destination.Tinder -> {
                        Pair(destination as Destination, getUriArguments(uri = uri, path = path, destinationArguments = destination.arguments))
                    }
                    else ->{
                        null
                    }
                }
            }
        }

        return null
    }

    fun findDestination(uri: Uri): Pair<Destination, List<String>>?{

        getHomeGraphDestination(uri)?.let { destination->
            return destination
        }

        return null
    }

}


