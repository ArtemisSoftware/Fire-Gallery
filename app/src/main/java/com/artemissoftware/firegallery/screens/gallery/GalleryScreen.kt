package com.artemissoftware.firegallery.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.screens.gallery.composables.GalleryCard
import com.artemissoftware.firegallery.screens.gallery.mappers.toUI
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GalleryScreen(
    navController: NavHostController,
    scaffoldState: FGScaffoldState
) {

    val viewModel: GalleryViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()


    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowErrorDialog -> {

                    val dialogType = DialogType.Error(
                        title = event.title,
                        description = event.message,
                        dialogOptions = DialogOptions(
                            confirmationTextId = R.string.accept,
                        )
                    )

                    scaffoldState.showDialog(dialogType)
                }
                else ->{}
            }

        }
    }

    BuildGalleryScreen(state = state.value, navController = navController)
}

@Composable
private fun BuildGalleryScreen(
    state: GalleryState,
    navController: NavHostController
) {

    FGScaffold(
        isLoading = state.isLoading,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(state.galleries) { gallery->

                GalleryCard(
                    gallery = gallery,
                    onClick = { galleryId->

                        navController.navigate(GalleryDestinations.Pictures.withCustomArgs(gallery.toUI()))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {

    val state = GalleryState(galleries = Gallery.galleryMockList)
    BuildGalleryScreen(state, rememberNavController())
}
