package com.artemissoftware.common.composables.images

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexstyl.swipeablecard.*
import com.artemissoftware.common.composables.FGCard
import com.artemissoftware.common.extensions.swipeablecard.Direction
import com.artemissoftware.common.extensions.swipeablecard.SwipeableCardState
import com.artemissoftware.common.extensions.swipeablecard.rememberSwipeableCardState
import com.artemissoftware.common.models.SwipeResult

@OptIn(ExperimentalSwipeableCardApi::class)
@ExperimentalMaterialApi
@Composable
fun FGSwipeCard(
    modifier: Modifier = Modifier,
    onSwiped: (result: SwipeResult) -> Unit,
    swipeState : SwipeableCardState,
    content: @Composable (BoxScope.() -> Unit)
) {

    FGCard(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = false, onClick = {})
            .swipableCard(
                state = swipeState,
                blockedDirections = listOf(Direction.Down),
                onSwiped = {
                    // swipes are handled by the LaunchedEffect
                    // so that we track button clicks & swipes
                    // from the same place
                    when(it) {
                        Direction.Right -> {
                            onSwiped(SwipeResult.ACCEPT)
                        }
                        Direction.Left -> {
                            onSwiped(SwipeResult.REJECT)
                        }
                        else -> {
                            onSwiped(SwipeResult.REJECT)
                        }
                    }
                },
                onSwipeCancel = {
                    Log.d("Swipeable-Card", "Cancelled swipe")
                },
            ),
        elevation = 12.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = Modifier,
            content = content
        )
    }

}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewSwipeCard() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        FGSwipeCard(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(
                    align = Alignment.Center
                ),
            onSwiped = { /*TODO*/ },
            swipeState = rememberSwipeableCardState(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
            ) {
                Text(

                    text = "Teste"
                )
            }
        }

    }
}