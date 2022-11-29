package com.artemissoftware.firegallery.screens.register

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object RegisterRoute : NavigationRoute<RegisterEvents, RegisterViewModel> {

    override fun getDestination() = DestinationRoutes.ProfileGraph.register

    @Composable
    override fun viewModel(): RegisterViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: RegisterViewModel) = RegisterScreen(viewModel = viewModel)
}