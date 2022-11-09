package com.artemissoftware.firegallery.screens.pictures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.grid.FGStaggeredVerticalGrid
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.screens.pictures.composables.PictureCard
import com.artemissoftware.firegallery.screens.profile.ProfileEvents
import com.artemissoftware.firegallery.ui.ManageUIEvents
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PicturesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    navController: NavHostController,
    scaffoldState: FGScaffoldState,
    viewModel: PicturesViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    ManageUIEvents(
        uiEvent = viewModel.uiEventLolo,
        scaffoldState = scaffoldState,
        onPopBackStack = onPopBackStack,
        onNavigate = onNavigate
    )

    BuildPicturesScreen(state = state.value, navController = navController, events = viewModel::onTriggerEvent)

}

@Composable
private fun BuildPicturesScreen(
    state: PictureState,
    events: ((PictureEvents) -> Unit)? = null,
    navController: NavHostController
) {

    FGScaffold(
        isLoading = state.isLoading,
        showTopBar = state.showOptions,
        title = state.galleryName,
        onNavigationClick = {
            navController.popBackStack()
        },
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            FGStaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(4.dp)
            ) {
                state.pictures.forEach { picture ->

                    PictureCard(
                        addFavoriteButton = state.isAuthenticated && picture.isFavorite,
                        picture = picture,
                        onClick = { pictureId ->
                            events?.invoke(PictureEvents.GoToPictureDetail(pictureId))
                        }
                    )

                }
            }

        }
    }

}



@Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {

    BuildPicturesScreen(state = PictureState(pictures = Picture.picturesMockList), navController = rememberNavController())
}