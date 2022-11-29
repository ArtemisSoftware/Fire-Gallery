package com.artemissoftware.firegallery.screens.profile

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes


object ProfileRoute : NavigationRoute<ProfileEvents, ProfileViewModel> {

    override fun getDestination() = DestinationRoutes.HomeGraph.profile

    @Composable
    override fun viewModel(): ProfileViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: ProfileViewModel) = ProfileScreen(viewModel = viewModel)
}