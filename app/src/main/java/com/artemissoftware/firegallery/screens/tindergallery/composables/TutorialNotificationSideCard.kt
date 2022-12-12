package com.artemissoftware.firegallery.screens.tindergallery.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.Purple200
import com.artemissoftware.common.theme.RedOrange


@Composable
fun TutorialNotificationSideCard(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
    animate: Boolean = false,
    onTutorialFinish: (() -> Unit)? = null
){

    MeasureUnconstrainedViewWidth(
        viewToMeasure = {
            Column(
                modifier = Modifier.background(color = Color.Red)

            ) {

                FGText(
                    modifier = Modifier.padding(8.dp),
                    text = "Winter season pictures"
                )
            }
        }
    ) { measuredWidth ->

        var currentState = remember { mutableStateOf(Tutot.PREVIEW) }

        var startAnimation = remember { mutableStateOf(animate) }

        LaunchedEffect(key1 = startAnimation.value) {
            currentState.value = if (startAnimation.value) Tutot.EXPAND else Tutot.PREVIEW
        }

        val retractedWidth =  -with(LocalDensity.current) { measuredWidth.toPx() }

        val optionHeight by animateIntAsState(
            targetValue = if (currentState.value == Tutot.PREVIEW) retractedWidth.toInt() else 0,
            animationSpec = tween(
                durationMillis = 2000, // duration
                delayMillis = 200, // delay before start animation
                easing = FastOutSlowInEasing
            ),
            finishedListener = {
                startAnimation.value = false

                if(!startAnimation.value && currentState.value == Tutot.PREVIEW) {
                    onTutorialFinish?.invoke()
                }
            }
        )


        Box(
            modifier = modifier
                .offset {

                    IntOffset(
                        optionHeight,
                        0
                    )
                }

        ) {
            NotificationSideCard(
                modifier = modifier,
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
private fun TutorialNotificationSideCardPreview() {
    TutorialNotificationSideCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}
