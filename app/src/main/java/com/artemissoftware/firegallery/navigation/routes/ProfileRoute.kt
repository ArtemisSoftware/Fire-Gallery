package com.artemissoftware.firegallery.navigation.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import com.artemissoftware.firegallery.navigation.HomeDestinations
import com.artemissoftware.firegallery.screens.profile.ProfileEvents
import com.artemissoftware.firegallery.screens.profile.ProfileScreen
import com.artemissoftware.firegallery.screens.profile.ProfileViewModel


object ProfileRoute : NavigationRoute<ProfileEvents, ProfileViewModel> {

    override fun getDestination() = DestinationRoutes.Home.profile

    @Composable
    override fun viewModel(): ProfileViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: ProfileViewModel) = ProfileScreen(viewModel = viewModel)
}