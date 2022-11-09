package com.artemissoftware.firegallery.screens.picturedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.animations.models.PulsatingType
import com.artemissoftware.common.composables.scaffold.FGBottomSheetScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.screens.picturedetail.composables.FavoriteButton
import com.artemissoftware.firegallery.screens.picturedetail.composables.PictureInformation
import com.artemissoftware.firegallery.screens.picturedetail.mappers.toUI
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PictureDetailScreen(
    onPopBackStack: () -> Unit,
    scaffoldState: FGScaffoldState,
    viewModel: PictureDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    ManageUiEvents(
        uiEvent = viewModel.uiEventLolo,
        onPopBackStack = onPopBackStack,
        scaffoldState = scaffoldState
    )


    BuildPictureDetailScreen(
        onPopBackStack = onPopBackStack,
        state = state.value,
        events = viewModel::onTriggerEvent
    )
}


@Composable
private fun BuildPictureDetailScreen(
    onPopBackStack: () -> Unit,
    state: PictureDetailState,
    events: ((PictureDetailEvents) -> Unit)? = null
) {

    FGBottomSheetScaffold(
        isLoading = state.isLoading,
        showTopBar = state.showToolbar(),
        onNavigationClick = {
            onPopBackStack.invoke()
        },
        topBarOptionComposable = {

            state.picture?.let { picture->

                if(state.isAuthenticated) {

                    FavoriteButton(
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


@Composable
private fun ManageUiEvents(
    uiEvent: Flow<UiEvent>,
    scaffoldState: FGScaffoldState,
    onPopBackStack: () -> Unit
) {

    LaunchedEffect(key1 = true) {

        uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowDialog -> {
                    scaffoldState.showDialog(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                else ->{}
            }
        }
    }
}





@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun BuildPictureDetailScreenPreview() {

    val state = PictureDetailState(picture = Picture.picturesMockList[0], isLoading = false)
    BuildPictureDetailScreen(onPopBackStack = {}, state = state)
}