package com.artemissoftware.common.extensions

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import com.artemissoftware.common.composables.animations.models.AlphaScaleAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import java.lang.Float.max

@Composable
fun scaleInfiniteTransition(
    initialValue: Float = 0f,
    targetValue: Float = 1f,
    durationMillis: Int,
): Float {
    val infiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    return scale
}


@Composable
fun createAsyncAnimation(delay : Int, initialValue: Float = 0f, targetValue: Float = 1f, initialAlpha : Float = 1f, targetAlpha : Float = 0f, durationMillis: Int = 1500) : AlphaScaleAnimation {
    val infiniteTransition = rememberInfiniteTransition()

    val pulsator = infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(delay)
        )
    )

    val alpha by derivedStateOf {
        val initialAlphaModifier = if(pulsator.value < initialAlpha) (1f - initialAlpha) else 0f
        max(targetAlpha, (1f - pulsator.value) - initialAlphaModifier)
    }

    return AlphaScaleAnimation(
        scale = pulsator.value,
        alpha = alpha
    )
}

@Composable
fun DrawCircleOnCanvas(
    circleSize : Dp,
    color: Color,
    baseDelay : Int = 0,
    animationDurationMillis : Int = 1500,
    initialScale : Float = 0f
) {
    val animation = createAsyncAnimation(delay = baseDelay, initialValue = initialScale, durationMillis = animationDurationMillis)
    Canvas(
        modifier = Modifier
            .size(circleSize)
    ) {
        drawCircle(
            color = color.copy(alpha = animation.alpha),
            center = Offset(
                x = size.width / 2,
                y = size.height / 2
            ),
            radius = ((circleSize * animation.scale) / 2f).toPx()
        )
    }
}