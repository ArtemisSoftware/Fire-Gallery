package com.artemissoftware.common.composables.button

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.theme.Orange
import com.artemissoftware.common.theme.RedOrange
import kotlinx.coroutines.launch

@Composable
fun FGAnimatableButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    preRevealColor: Color = Orange,
    borderColor: Color = RedOrange,
    logoColor: Color = Color.Red,
    shouldAnimate : Boolean = false,
    onAnimationFinish: () -> Unit = {},
    iconSize : Dp = 140.dp,
    onClick : () -> Unit = {}
) {
    BuildFGAnimatableButton(
        modifier = modifier,
        centerImage = {
            Image(modifier = Modifier.size(iconSize), imageVector = imageVector, contentDescription = "Center Button", colorFilter = ColorFilter.tint(logoColor))
        },
        preRevealColor = preRevealColor,
        borderColor = borderColor,
        shouldAnimate = shouldAnimate,
        onAnimationFinish = onAnimationFinish,
        onClick = onClick
    )
}

@Composable
fun FGAnimatableButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    preRevealColor: Color = Orange,
    borderColor: Color = RedOrange,
    logoColor: Color = Color.Red,
    shouldAnimate : Boolean = false,
    onAnimationFinish: () -> Unit = {},
    iconSize : Dp = 140.dp,
    onClick : () -> Unit = {}
) {
    BuildFGAnimatableButton(
        modifier = modifier,
        centerImage = {
            Image(modifier = Modifier.size(iconSize), painter = painter, contentDescription = "Center Button", colorFilter = ColorFilter.tint(logoColor))
        },
        preRevealColor = preRevealColor,
        borderColor = borderColor,
        shouldAnimate = shouldAnimate,
        onAnimationFinish = onAnimationFinish,
        onClick = onClick
    )
}

@Composable
private fun BuildFGAnimatableButton(
    modifier: Modifier = Modifier,
    centerImage: @Composable () -> Unit,
    preRevealColor: Color = Orange,
    borderColor: Color = RedOrange,
    shouldAnimate : Boolean = true,
    onAnimationFinish: () -> Unit = {},
    onClick : () -> Unit = {}
) {
    val boxSize = 180.dp
    val borderWidth = 2.dp

    val animatedBorderWidth = remember { Animatable(boxSize.value) }
    val animateColor = remember { Animatable(preRevealColor) }
    var startAnimation by remember { mutableStateOf(!shouldAnimate) }


    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 400.dp,
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    LaunchedEffect(true) {

        startAnimation = true

        if(shouldAnimate) {
            val jobA = launch {
                animatedBorderWidth.animateTo(
                    targetValue = borderWidth.value,
                    animationSpec = tween(
                        durationMillis = 1500,
                        easing = LinearEasing,
                        delayMillis = 10
                    )
                )
            }

            val jobB = launch {
                animateColor.animateTo(
                    targetValue = borderColor,
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = LinearEasing,
                        delayMillis = 100
                    )
                )

            }


            jobA.join()
            jobB.join()
        }
        onAnimationFinish.invoke()
    }

    val actualBorderWidth = if(shouldAnimate) animatedBorderWidth.value else borderWidth.value
    val actualBorderColor = if(shouldAnimate) animateColor.value else borderColor

    Box(
        modifier = modifier
            .size(boxSize)
            .offset(y = offsetState)
            .alpha(alpha = alphaState)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(color = preRevealColor)
            .border(
                width = Dp(actualBorderWidth),
                color = actualBorderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center

    ) {
        Box(
            modifier = Modifier.size(140.dp),
            contentAlignment = Alignment.Center
        ) {
            centerImage()

        }
    }
}

@Preview
@Composable
fun FGAnimatableButtonPreview() {
    FGAnimatableButton(
        imageVector = Icons.Default.Refresh,
        shouldAnimate = false
    )
}