package com.artemissoftware.firegallery.ui

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


    fun executeDeepLink___(navController: NavHostController){
        deepLink.value?.let{
            try {




                val destination = DestinationRoutes().findRoute(it)
                changeCurrentPositionBottomBar_(2, destination = destination!!.getRoutel(), navController = navController)



            }
            catch(e: IllegalArgumentException){
                //TODO: solucao mais bonita, mas a que está é boa
                //navController.navigate(it)
            }

        }

        deepLink.value = null
    }
}