package com.artemissoftware.firegallery.screens.pictures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.grid.FGStaggeredVerticalGrid
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.screens.pictures.composables.PictureCard

@Composable
fun PicturesScreen(
    viewModel: PicturesViewModel
) {

    val state = viewModel.state.collectAsState()


    BuildPicturesScreen(
        state = state.value,
        events = viewModel::onTriggerEvent
    )

}

@Composable
private fun BuildPicturesScreen(
    state: PictureState,
    events: ((PictureEvents) -> Unit)? = null
) {

    FGScaffold(
        isLoading = state.isLoading,
        showTopBar = state.showOptions,
        title = state.galleryName,
        onNavigationClick = {
            events?.invoke(PictureEvents.PopBackStack)
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

    BuildPicturesScreen(state = PictureState(pictures = Picture.picturesMockList))
}