package com.artemissoftware.firegallery.screens.tindergallery

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGPulsatingButton
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.extensions.swipeablecard.Direction
import com.artemissoftware.common.extensions.swipeablecard.SwipeableCardState
import com.artemissoftware.common.models.SwipeResult
import com.artemissoftware.common.theme.FGStyle.TextAlbertSansBold28
import com.artemissoftware.common.theme.RedOrange
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.tindergallery.composables.*
import kotlinx.coroutines.launch
import java.lang.Integer.max

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TinderGalleryScreen(viewModel : TinderGalleryViewModel) {

    val state = viewModel.state.value

    BuildTinderGalleryScreen(state = state, events = viewModel::onTriggerEvent)
}


@Composable
fun BuildTinderGalleryScreen(
    state : TinderGalleyState,
    events : (TinderGalleryEvents) -> Unit = {},
) {

    Box{

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val scope = rememberCoroutineScope()

            var currentOffset by remember {
                mutableStateOf(Offset(0f,0f))
            }

            var shouldReset by remember {
                mutableStateOf(false)
            }

            val resetOffset = animateOffsetAsState(
                targetValue = if(shouldReset) Offset(0f,0f) else currentOffset,
                animationSpec = tween(300, easing = LinearEasing),
                finishedListener = {
                    if(shouldReset){
                        currentOffset = Offset(0f, 0f)
                        shouldReset = false
                    }

                }
            )

            var states by remember {
                mutableStateOf<Map<Picture, SwipeableCardState>>(emptyMap())
            }

            val screenWidth = with(LocalDensity.current) {
                LocalConfiguration.current.screenWidthDp.dp.toPx()
            }
            val screenHeight = with(LocalDensity.current) {
                LocalConfiguration.current.screenHeightDp.dp.toPx()
            }


            LaunchedEffect(key1 = state.pictures) {
                states = state.pictures.reversed().associateWith {
                    SwipeableCardState(
                        maxWidth = screenWidth,
                        maxHeight = screenHeight
                    )
                }
            }

            FGText(text = stringResource(R.string.swipe_to_add_photos_to_your_favorites), style = TextAlbertSansBold28)

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                states.forEach { (picture, state) ->

                    TinderPictureCard(
                        modifier = Modifier.clickable(enabled = false, onClick = {}),
                        picture = picture,
                        onSwiped = {
                            shouldReset = true
                            events(TinderGalleryEvents.GoToNextPicture(it))
                        },
                        onDrag = {
                            currentOffset = it
                        },
                        swipeState = state
                    )
                }

                if(!state.showAddMoreButton()) {

                    TinderActionsRow(
                        currentXOffset = currentOffset.x,
                        resetOffset = resetOffset.value,
                        shouldReset = shouldReset,
                        maxWidth = constraints.maxWidth.toFloat(),
                        maxHeight = constraints.maxHeight.toFloat(),
                        onClick = {
                            scope.launch {
                                val currentPicture = states[state.getCurrentPicture()]
                                when(it) {
                                    SwipeResult.REJECT -> {
                                        currentPicture?.swipe(Direction.Left)
                                    }
                                    SwipeResult.ACCEPT -> {
                                        currentPicture?.swipe(Direction.Right)
                                    }
                                }
                                shouldReset = true
                                events(TinderGalleryEvents.GoToNextPicture(it))
                            }
                        },
                    )
                }

                FGPulsatingButton(
                    modifier = Modifier.align(Alignment.Center),
                    pulseColor = RedOrange,
                    onClick = {
                        events(TinderGalleryEvents.FetchMorePictures)
                    },
                    imageVector = Icons.Default.Refresh,
                    visible = state.showAddMoreButton(),
                )
            }
        }


        state.notificationMessage?.let {
            SwipeableNotification(
                modifier = Modifier.padding(top = 188.dp),
                text = it,
                imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png",
                showTutorial = true
            )
        }



    }


}


@Preview
@Composable
fun TinderGalleryScreenPreview() {
    var state by remember {
        mutableStateOf(TinderGalleyState(
            pictures = Picture.picturesMockList
        ))
    }
    BuildTinderGalleryScreen(
        state = state,
        events = {
            when(it) {
                is TinderGalleryEvents.GoToNextPicture -> {
                    state = state.copy(
                        currentIndex = max(state.currentIndex - 1, 0)
                    )
                }
                else -> {}
            }
        }
    )
}