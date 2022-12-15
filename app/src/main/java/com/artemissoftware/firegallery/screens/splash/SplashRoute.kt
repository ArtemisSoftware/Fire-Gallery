package com.artemissoftware.firegallery.screens.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object SplashRoute : NavigationRoute<SplashEvents, SplashViewModel> {

    override fun getDestination() = DestinationRoutes.RootGraph.splash

    @Composable
    override fun viewModel(): SplashViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SplashViewModel) = SplashScreen(viewModel = viewModel)
}