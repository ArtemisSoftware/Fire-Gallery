package com.artemissoftware.firegallery.screens.tindergallery

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object TinderGalleryRoute : NavigationRoute<TinderGalleryEvents, TinderGalleryViewModel> {

    override fun getDestination() = DestinationRoutes.Home.tinderGallery

    @Composable
    override fun viewModel(): TinderGalleryViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: TinderGalleryViewModel) = TinderGalleryScreen(viewModel = viewModel)
}