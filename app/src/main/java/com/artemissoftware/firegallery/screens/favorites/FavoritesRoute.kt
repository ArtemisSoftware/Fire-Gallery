package com.artemissoftware.firegallery.screens.favorites

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object FavoritesRoute : NavigationRoute<FavoriteEvents, FavoritesViewModel> {

    override fun getDestination() = DestinationRoutes.HomeGraph.favorites

    @Composable
    override fun viewModel(): FavoritesViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: FavoritesViewModel) = FavoritesScreen(viewModel = viewModel)
}