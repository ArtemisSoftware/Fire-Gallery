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


    fun executeDeepLink___(navController: NavHostController){//TODO: experimentar com caminho inválido
        deepLink.value?.let{
            try {

                //TODO o que fazer  quando devolver null????
                DestinationRoutes().findDestination(it)?.let { destination->
                    executeDeepLink(navController = navController, destinationWithArguments = destination)
                }

            }
            catch(e: IllegalArgumentException){
                //TODO: solucao mais bonita, mas a que está é boa
                //navController.navigate(it)
            }
        }
        deepLink.value = null
    }

    fun redirect(uri:String, navController: NavHostController){//TODO: experimentar com caminho inválido

        DestinationRoutes().findDestination(Uri.parse(uri))?.let { destination ->
            executeDeepLink(navController = navController, destinationWithArguments = destination)
        }

    }
}