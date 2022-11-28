package com.artemissoftware.firegallery.screens.gallery

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object GalleryRoute: NavigationRoute<GalleryEvents, GalleryViewModel> {

    override fun getDestination() = DestinationRoutes.Home.gallery

    @Composable
    override fun viewModel(): GalleryViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: GalleryViewModel) = GalleryScreen(viewModel = viewModel)
}