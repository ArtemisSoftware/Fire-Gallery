package com.artemissoftware.firegallery.screens.picturedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.animations.models.PulsatingType
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.scaffold.FGBottomSheetScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.picturedetail.composables.FavoriteButton
import com.artemissoftware.firegallery.screens.picturedetail.composables.PictureInformation
import com.artemissoftware.firegallery.screens.picturedetail.mappers.toUI
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PictureDetailScreen(
    navController: NavHostController,
    scaffoldState: FGScaffoldState,
    viewModel: PictureDetailViewModel = hiltViewModel()
) {

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
                            confirmation = { navController.popBackStack() }
                        )
                    )

                    scaffoldState.showDialog(dialogType)

                }
                else ->{}
            }
        }
    }


    BuildPictureDetailScreen(
        state = state.value,
        events = viewModel::onTriggerEvent,
        navController = navController
    )
}


@Composable
private fun BuildPictureDetailScreen(
    navController: NavHostController,
    state: PictureDetailState,
    events: ((PictureDetailEvents) -> Unit)? = null
) {


    FGBottomSheetScaffold(
        isLoading = state.isLoading,
        showTopBar = state.picture != null,
        onNavigationClick = {
            navController.popBackStack()
        },
        topBarOptionComposable = {

            state.picture?.let { picture-> //TODO: so deve aparecer se user estiver logado

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
            Content(state.picture)
        }
    )
}

@Composable
private fun Content(picture: Picture?) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(picture?.imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    Box(modifier = Modifier.fillMaxSize()){

        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }

}





@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun BuildPictureDetailScreenPreview() {

    val nav = rememberNavController()
    val state = PictureDetailState(picture = Picture.picturesMockList[0], isLoading = false)
    BuildPictureDetailScreen(nav, state = state)
}