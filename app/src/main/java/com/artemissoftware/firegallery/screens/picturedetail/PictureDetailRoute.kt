package com.artemissoftware.firegallery.screens.picturedetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.firegallery.navigation.routes.NavigationRoute
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes

object PictureDetailRoute : NavigationRoute<PictureDetailEvents, PictureDetailViewModel> {

    override fun getDestination() = DestinationRoutes.GalleryGraph.pictureDetail

    @Composable
    override fun viewModel(): PictureDetailViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: PictureDetailViewModel) = PictureDetailScreen(viewModel = viewModel)
}