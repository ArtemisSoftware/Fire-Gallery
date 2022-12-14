package com.artemissoftware.firegallery.navigation.routes.destinations

import android.net.Uri
import com.artemissoftware.firegallery.navigation.NavigationArguments
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

        val formattedPath = path.toLowerCase()
        return destinations.find { formattedPath.contains(it.route.toLowerCase())  }
    }


    private fun getHomeGraphDestination(uri: Uri): Pair<Destination, List<String>>? {

        uri.path?.let { path->

            getDestination(HomeGraph.getRoutes(), path)?.let { destination->

                return when (destination) {

                    is Destination.Tinder -> {

                        val arguments = mutableListOf<String>()

                        uri.getQueryParameter(NavigationArguments.SEASON)?.let { argument-> arguments.add(argument) }

                        Pair(destination as Destination, arguments)

                    }
                    else ->{
                        null
                    }
                }
            }
        }

        return null
    }


    //TODO o que fazer  quando devolver null????
    fun findDestination(uri: Uri): Pair<Destination, List<String>>?{

        getHomeGraphDestination(uri)?.let { destination->
            return destination
        }

        return null

    }


//    Uri uri = Uri.parse("http://www.chalklit.in/post.html?chapter=V-Maths-Addition%20&%20Subtraction&post=394");
//    String server = uri.getAuthority();
//    String path = uri.getPath();
//    String protocol = uri.getScheme();
//    Set<String> args = uri.getQueryParameterNames();
//    Then you can even get a specific element from the query parameters as such;
//
//    String chapter = uri.getQueryParameter("chapter");  //will return "V-Maths-Addition "
}


