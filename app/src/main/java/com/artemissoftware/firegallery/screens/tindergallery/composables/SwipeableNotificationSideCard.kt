package com.artemissoftware.firegallery.screens.tindergallery.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.Purple200
import com.artemissoftware.common.theme.RedOrange
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableNotification(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
) {


    var currentState = remember { mutableStateOf(Tutot.TUTORIAL) }

    when(currentState.value){
        Tutot.EXPAND -> {

        }
        Tutot.PREVIEW -> {

            SwipeableNotificationSideCard(
                modifier = modifier,
                text = text,
                imageUrl = imageUrl,
                endBorderColor = endBorderColor,
                startBorderColor = startBorderColor
            )
        }
        Tutot.TUTORIAL -> {
            TutorialNotificationSideCard(
                modifier = modifier,
                text = text,
                imageUrl = imageUrl,
                endBorderColor = endBorderColor,
                startBorderColor = startBorderColor,
                animate = true,
                onTutorialFinish = {
                    currentState.value = Tutot.PREVIEW
                }
            )

        }
    }

}

@Preview
@Composable
private fun SwipeableNotificationPreview() {
    SwipeableNotification(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableNotificationSideCard(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
) {


    MeasureUnconstrainedViewWidth(
        viewToMeasure = {
            Column(
                modifier = Modifier
            ) {

                FGText(
                    modifier = Modifier.padding(8.dp),
                    text = "Winter season pictures"
                )
            }
        }
    ) { measuredWidth ->

        val swipeableState = rememberSwipeableState(0)
        val retractedWidth =  with(LocalDensity.current) { measuredWidth.toPx() }
        val anchors = mapOf(-retractedWidth to 0, 0f to 1)

        Box(
            modifier = modifier
                .offset {
                    IntOffset(
                        swipeableState.offset.value.roundToInt(),
                        0
                    )
                }
                .swipeable(
                    enabled = swipeableState.offset.value.roundToInt() < 1,
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.6f) },
                    orientation = Orientation.Horizontal
                )

        ) {
            NotificationSideCard(
                text = text,
                imageUrl = imageUrl,
                endBorderColor = endBorderColor,
                startBorderColor = startBorderColor
            )
        }
    }
}



@Preview
@Composable
private fun SwipeableNotificationSideCardPreview() {
    SwipeableNotificationSideCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}

