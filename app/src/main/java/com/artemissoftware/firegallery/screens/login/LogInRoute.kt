package com.artemissoftware.firegallery.screens.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object LogInRoute : NavigationRoute<LogInEvents, LogInViewModel> {

    override fun getDestination() = DestinationRoutes.Profile.login

    @Composable
    override fun viewModel(): LogInViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: LogInViewModel) = LogInScreen(viewModel = viewModel)
}