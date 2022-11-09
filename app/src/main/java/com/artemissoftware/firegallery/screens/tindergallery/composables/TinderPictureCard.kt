package com.artemissoftware.firegallery.screens.tindergallery.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.images.FGSwipeCard
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.extensions.swipeablecard.SwipeableCardState
import com.artemissoftware.common.extensions.swipeablecard.rememberSwipeableCardState
import com.artemissoftware.common.models.SwipeResult
import com.artemissoftware.common.theme.FGStyle.TextAlbertSansBold28
import com.artemissoftware.domain.models.Picture

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TinderPictureCard(
    modifier: Modifier = Modifier,
    picture: Picture,
    onSwiped : (SwipeResult) -> Unit = {},
    onDrag : (Offset) -> Unit,
    swipeState : SwipeableCardState = rememberSwipeableCardState()
) {
    LaunchedEffect(key1 = swipeState.offset.value) {
        onDrag(swipeState.offset.value)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(picture.imageUrl)
            .size(Size.ORIGINAL)
            .crossfade(500)
            .build()
    )

    if(swipeState.swipedDirection == null) {
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            FGSwipeCard(
                modifier = Modifier
                    .clickable(enabled = false, onClick = {})
                    .fillMaxSize(),
                onSwiped = onSwiped,
                swipeState = swipeState,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Image(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize(),
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                    )

                    FGText(
                        text = picture.title,
                        style = TextAlbertSansBold28,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp)
                    )
                }
            }
        }
    }

}


@Preview(device = Devices.NEXUS_5X, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TinderPictureCardPreview() {
    BoxWithConstraints(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TinderPictureCard(
            Modifier,
            Picture.picturesMockList[0],
            {},
            {}
        )
    }
}