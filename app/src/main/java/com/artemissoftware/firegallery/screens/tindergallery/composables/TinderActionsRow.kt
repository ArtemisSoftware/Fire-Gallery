package com.artemissoftware.firegallery.screens.tindergallery.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.extensions.swipeablecard.Direction
import com.artemissoftware.common.models.SwipeResult
import kotlinx.coroutines.launch

@Composable
fun BoxScope.TinderActionsRow(
    shouldReset : Boolean = false,
    resetOffset : Offset = Offset(0f, 0f),
    currentXOffset : Float,
    maxWidth : Float,
    maxHeight : Float,
    onClick : (SwipeResult) -> Unit,
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TinderActionButton(
            pretendedAction = SwipeResult.REJECT,
            image = Icons.Default.Cancel,
            offsetX = if(shouldReset) resetOffset.x else currentXOffset,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            size = 64.dp,
            color = Color.Red,
            onClick = {
                onClick(SwipeResult.REJECT)
            }
        )

        TinderActionButton(
            pretendedAction = SwipeResult.ACCEPT,
            image = Icons.Default.Favorite,
            offsetX = if(shouldReset) resetOffset.x else currentXOffset,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            size = 64.dp,
            color = Color(31, 233, 184),
            onClick = {
                onClick(SwipeResult.ACCEPT)
            }
        )
    }
}

@Preview
@Composable
fun TinderActionRowPreview() {
    BoxWithConstraints() {
        TinderActionsRow(
            currentXOffset = 0f,
            maxWidth = constraints.maxWidth.toFloat(),
            maxHeight = constraints.maxHeight.toFloat(),
            onClick = {},
        )

    }

}