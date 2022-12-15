package com.artemissoftware.firegallery.ui

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.profile.User
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import kotlinx.coroutines.CoroutineScope

class FG_ScaffoldState(scope: CoroutineScope?) : FGScaffoldState(scope) {

    var user = mutableStateOf<User?>(null)
        private set


    fun setUser(user: User?) {
        this.user.value = user
    }

    fun isLoggedIn() = this.user.value != null


    fun executeDeepLink(navController: NavHostController){//TODO: experimentar com caminho inválido
        deepLink.value?.let{
            findDestinationAndNavigateTo(uri = it, navController = navController)
        }
        deepLink.value = null
    }

    fun redirect(uri:String, navController: NavHostController){//TODO: experimentar com caminho inválido
        findDestinationAndNavigateTo(uri = Uri.parse(uri), navController = navController)
    }

    private fun findDestinationAndNavigateTo(uri:Uri, navController: NavHostController){

        try {

            //TODO o que fazer  quando devolver null????
            DestinationRoutes().findDestination(uri)?.let { destination->
                executeDeepLink(navController = navController, destinationWithArguments = destination)
            } ?: kotlin.run {
                navController.navigate(uri)
            }

        }
        catch(e: IllegalArgumentException){
            //TODO: solucao mais bonita, mas a que está é boa
            //navController.navigate(it)
        }
    }
}