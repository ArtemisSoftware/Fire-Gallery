package com.artemissoftware.firegallery.screens.picturedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.animations.models.PulsatingType
import com.artemissoftware.common.composables.scaffold.FGBottomSheetScaffold
import com.artemissoftware.common.theme.LightBlue
import com.artemissoftware.common.theme.PlateShape
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.screens.picturedetail.composables.FavoriteButton
import com.artemissoftware.firegallery.screens.picturedetail.composables.PictureInformation
import com.artemissoftware.firegallery.screens.picturedetail.mappers.toUI

@Composable
fun PictureDetailScreen(
    viewModel: PictureDetailViewModel
) {

    val state = viewModel.state.collectAsState()

    BuildPictureDetailScreen(
        state = state.value,
        events = viewModel::onTriggerEvent
    )
}


@Composable
private fun BuildPictureDetailScreen(
    state: PictureDetailState,
    events: ((PictureDetailEvents) -> Unit)? = null
) {

    FGBottomSheetScaffold(
        isLoading = state.isLoading,
        showTopBar = state.showToolbar(),
        onNavigationClick = {
            events?.invoke(PictureDetailEvents.PopBackStack)
        },
        topBarOptionComposable = {

            state.picture?.let { picture->

                if(state.isAuthenticated) {

                    FavoriteButton(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(PlateShape)
                            .background(
                                brush = Brush.linearGradient(colors = listOf(LightBlue, Color.White)),
                                shape = PlateShape
                            )
                            .padding(4.dp),
                        size = 28.dp,
                        isFavorite = state.isFavorite,
                        pulsatingType = PulsatingType.LIMITED,
                        onClickToFavorite = {
                            events?.invoke(PictureDetailEvents.FavoritePicture(picture.id, true))
                        },
                        onClickToRemoverFavorite = {
                            events?.invoke(PictureDetailEvents.FavoritePicture(picture.id, false))
                        },
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 46.dp),
        sheetContent = {

            PictureInformation(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp),
                picture = state.picture?.toUI()
            )

        },
        content = {
            ImageDisplay(state.picture)
        }
    )
}

@Composable
private fun ImageDisplay(picture: Picture?) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(picture?.imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ){

        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    }

}





@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun BuildPictureDetailScreenPreview() {

    val state = PictureDetailState(picture = Picture.picturesMockList[0], isLoading = false)
    BuildPictureDetailScreen(state = state)
}