package com.artemissoftware.firegallery.screens.favorites

import androidx.compose.animation.AnimatedVisibility
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
import com.artemissoftware.firegallery.screens.pictures.PictureState
import com.artemissoftware.firegallery.screens.pictures.composables.PictureCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel
){

    val state = viewModel.state.collectAsState()

    BuildFavoritesScreen(state = state.value, events = viewModel::onTriggerEvent)

}

@Composable
private fun BuildFavoritesScreen(
    state: PictureState,
    events: ((FavoriteEvents) -> Unit)? = null
) {

    FGScaffold(isLoading = state.isLoading) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            FGStaggeredVerticalGrid(
                numColumns = 2, //put the how many column you want
                modifier = Modifier.padding(4.dp)
            ) {
                state.pictures.forEach { picture ->

                    AnimatedVisibility(visible = state.isFavorite(pictureId = picture.id)) {
                        PictureCard(
                            addFavoriteButton = true,
                            picture = picture,
                            onFavoriteClick = { pictureId ->
                                events?.invoke(FavoriteEvents.Remove(pictureId))
                            },
                            onClick = { pictureId ->
                                events?.invoke(FavoriteEvents.GoToPictureDetail(pictureId))
                            }
                        )
                    }
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
private fun BuildFavoritesScreenPreview() {

    BuildFavoritesScreen(state = PictureState(pictures = Picture.picturesMockList))
}