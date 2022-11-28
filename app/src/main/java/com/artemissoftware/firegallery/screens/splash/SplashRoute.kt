package com.artemissoftware.firegallery.screens.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import com.artemissoftware.firegallery.screens.SplashScreen

object SplashRoute : NavigationRoute<SplashEvents, SplashViewModel> {

    override fun getDestination() = DestinationRoutes.Root.splash

    @Composable
    override fun viewModel(): SplashViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SplashViewModel) = SplashScreen(viewModel = viewModel)
}