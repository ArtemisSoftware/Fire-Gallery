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
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PicturesScreen(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    val viewModel: PicturesViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowInfoDialog -> {

                    val dialogType = DialogType.Info(
                        title = event.title,
                        description = event.message,
                        dialogOptions = DialogOptions(
                            confirmationTextId = R.string.accept,
                            confirmation = {
                                navController.popBackStack()
                            }
                        )
                    )

                    scaffoldState.showDialog(dialogType)
                }
                else ->{}
            }
        }
    }

    BuildPicturesScreen(state = state.value, navController = navController)

}

@Composable
private fun BuildPicturesScreen(
    state: PictureState,
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
                        picture = picture,
                        onClick = { pictureId ->
                            navController.navigate(GalleryDestinations.PictureDetail.withArgs(pictureId))
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