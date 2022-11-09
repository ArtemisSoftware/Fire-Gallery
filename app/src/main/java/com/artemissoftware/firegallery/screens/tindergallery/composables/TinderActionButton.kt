package com.artemissoftware.firegallery.screens.tindergallery.composables

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGCircularActionButton
import com.artemissoftware.common.composables.button.FGCircularButton
import com.artemissoftware.common.models.SwipeResult
import java.lang.Float.min
import kotlin.math.absoluteValue

@Composable
fun TinderActionButton(
    modifier: Modifier = Modifier,
    offsetX : Float,
    size : Dp = 48.dp,
    maxWidth : Float,
    maxHeight : Float,
    pretendedAction : SwipeResult,
    image : ImageVector,
    onClick : () -> Unit,
    color : Color
) {
    val iconColorRed = color.red * (1f - min(offsetX.absoluteValue, maxWidth) / maxWidth) + Color.White.red * (min(offsetX.absoluteValue, maxWidth) / maxWidth)
    val iconColorGreen = color.green * (1f - min(offsetX.absoluteValue, maxWidth) / maxWidth) + Color.White.green * (min(offsetX.absoluteValue, maxWidth) / maxWidth)
    val iconColorBlue = color.blue * (1f - min(offsetX.absoluteValue, maxWidth) / maxWidth) + Color.White.blue * (min(offsetX.absoluteValue, maxWidth) / maxWidth)

    val iconColor = Color(iconColorRed, iconColorGreen, iconColorBlue)
    FGCircularActionButton(
        modifier = modifier
            .graphicsLayer {
                swipeActionButtonMove(
                    offsetX = offsetX,
                    buttonSize = size,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    pretendedAction = pretendedAction,
                )
            }
        ,
        imageVector = image,
        buttonSize = size,
        color = iconColor,
        onClick = onClick,
        backgroundColor = color.copy(alpha = min(offsetX.absoluteValue, maxWidth) / maxWidth)
    )
}

fun GraphicsLayerScope.swipeActionButtonMove(
    offsetX : Float,
    buttonSize : Dp,
    maxHeight : Float,
    maxWidth: Float,
    maxSizeScale : Float = 2f,
    pretendedAction : SwipeResult,
) {

    val currentYPosition = -java.lang.Float.min(
        buttonSize.toPx(),
        offsetX.absoluteValue * (maxHeight/ maxWidth)
    )

    if (pretendedAction == SwipeResult.getFromOffset(offsetX)) {
        val scale = java.lang.Float.min(
            maxSizeScale,
            1 + offsetX.absoluteValue / maxWidth
        )

        scaleX = scale
        scaleY = scale
    } else {
        val scale = java.lang.Float.max(
            0f,
            1 - (offsetX.absoluteValue / maxWidth) * 3
        )

        scaleX = scale
        scaleY = scale
    }
}