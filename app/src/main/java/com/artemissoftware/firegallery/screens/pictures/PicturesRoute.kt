package com.artemissoftware.firegallery.screens.pictures

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object PicturesRoute : NavigationRoute<PictureEvents, PicturesViewModel> {

    override fun getDestination() = DestinationRoutes.GalleryGraph.pictures

    @Composable
    override fun viewModel(): PicturesViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: PicturesViewModel) = PicturesScreen(viewModel = viewModel)
}