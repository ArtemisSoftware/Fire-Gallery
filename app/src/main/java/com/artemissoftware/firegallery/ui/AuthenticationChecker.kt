package com.artemissoftware.firegallery.ui

import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

@Composable
fun AuthenticationChecker(
    scaffoldState: FG_ScaffoldState,
    finalRoute: Bundle? = null,
    required: Boolean,
    redirect: (String) -> Unit,
    content: @Composable () -> Unit,
) {

    if(required && !scaffoldState.isLoggedIn()) {

        LaunchedEffect(key1 = true) {
            val redirectRoute = getRedirectRoute(finalRoute)
            redirect.invoke(redirectRoute)
        }
    }
    else{
        content()
    }
}

private fun getRedirectRoute(finalRoute: Bundle?): String {
    var redirectRoute = ""

    finalRoute?.let { bundle->
        (bundle.get(NavController.KEY_DEEP_LINK_INTENT) as Intent).data?.let { uri->
            redirectRoute = uri.toString()
        }
    }

    return DestinationRoutes.ProfileGraph.login.withArgs(redirectRoute)
}