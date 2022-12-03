package com.artemissoftware.firegallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

@Composable
fun AuthenticationChecker(
    scaffoldState: FG_ScaffoldState,
    required: Boolean,
    redirect: () -> Unit,
) {

    if(required && !scaffoldState.isLoggedIn()) {
        LaunchedEffect(key1 = scaffoldState) {
            redirect.invoke()
        }
    }
}