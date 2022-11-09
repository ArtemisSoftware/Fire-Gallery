package com.artemissoftware.common.composables.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.common.extensions.DrawCircleOnCanvas

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FGPulsatingButton(
    modifier : Modifier = Modifier,
    buttonColor : Color = MaterialTheme.colors.primary,
    pulseColor : Color = buttonColor,
    shouldAnimate : Boolean = true,
    animationDurationMillis : Int= 1500,
    onClick : () -> Unit,
    imageVector : ImageVector,
    visible : Boolean = true
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        if(shouldAnimate && visible) {
            DrawCircleOnCanvas(
                circleSize = with(LocalDensity.current) {
                    constraints.maxWidth.toDp()
                },
                color = pulseColor,
                animationDurationMillis = animationDurationMillis,
            )

            DrawCircleOnCanvas(
                circleSize = with(LocalDensity.current) {
                    constraints.maxWidth.toDp()
                },
                color = pulseColor,

                baseDelay = animationDurationMillis / 2,
                animationDurationMillis = animationDurationMillis,
            )

            DrawCircleOnCanvas(
                circleSize = with(LocalDensity.current) {
                    constraints.maxWidth.toDp()
                },
                color = pulseColor,
                baseDelay = animationDurationMillis / 4,
                animationDurationMillis = animationDurationMillis,
            )


        }
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            FGAnimatableButton(modifier = Modifier.align(Alignment.Center),imageVector = imageVector, onClick = onClick)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FGPulsatingButtonPreview() {
    FGPulsatingButton(buttonColor = MaterialTheme.colors.primary, onClick = {}, imageVector = Icons.Default.Refresh)
}