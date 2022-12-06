package com.artemissoftware.firegallery.screens.tindergallery.composables

import android.annotation.SuppressLint
import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.artemissoftware.firegallery.R
import com.google.common.io.Files.append
import kotlin.math.roundToInt

//@SuppressLint("UnusedTransitionTargetStateParameter")
//@Composable
//fun NotificationPop( isRevealed: Boolean,
//                     cardOffset: Float,
//                     onExpand: () -> Unit,
//                     onCollapse: () -> Unit,
//) {
//    val offsetX by remember { mutableStateOf(0f) }
//    val transitionState = remember {
//        MutableTransitionState(isRevealed).apply {
//            targetState = !isRevealed
//        }
//    }
//    val transition = updateTransition(transitionState)
//    val offsetTransition by transition.animateFloat(
//        label = "cardOffsetTransition",
//        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
//        targetValueByState = { if (isRevealed) cardOffset - offsetX else -offsetX },
//    )
//
//    Card(
//        modifier = Modifier
//            .offset { IntOffset((offsetX + offsetTransition).roundToInt(), 0) }
//            .pointerInput(Unit) {
//                detectHorizontalDragGestures { change, dragAmount ->
//
//                }
//            },
//        content = { CardDemo() }
//    )
//}

@Composable
fun CardDemo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("welcome to ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append("Jetpack Compose Playground")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("Now you are in the ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Card")
                    }
                    append(" section")
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterialApi
@Composable
fun swipeToDismiss() {
    val dismissState = rememberDismissState(initialValue = DismissValue.Default)


    SwipeToDismiss(
        state = dismissState,
        /***  create dismiss alert Background */
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.StartToEnd -> Color.Green
                DismissDirection.EndToStart -> Color.Red
                null -> Color.Transparent
            }
            val direction = dismissState.dismissDirection

            if (direction == DismissDirection.StartToEnd) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Move to Archive", fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.heightIn(5.dp))
                        Text(
                            text = "Move to Bin",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )

                    }
                }
            }
        },
        /**** Dismiss Content */
        dismissContent = {
            CardDemo()
        },
        /*** Set Direction to dismiss */
        directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
    )
}


@ExperimentalAnimationApi
@Composable
private fun ExpandableFabAnimateColorSizePosition(modifier: Modifier = Modifier,) {

    val localDensity = LocalDensity.current

    var columnHeightDp by remember {
        mutableStateOf(0f)
    }

    val expand = remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .background(Color.Blue)
        .animateContentSize()
        .clickable { expand.value = expand.value.not() }) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = if (expand.value)
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            else
                "Click Me!",
        )
    }

//    val transition = updateTransition(expand.value, label = "")
//
//    val contentColor = transition.animateColor(label = "") { expanded ->
//        when (expanded) {
//            true -> Color.Green
//            false -> Color.Blue
//        }
//    }
//    val contentSize = transition.animateIntSize(label = "") { expanded ->
//        when (expanded) {
//            true -> IntSize(columnHeightDp.toInt(), 20)
//            false -> IntSize.Zero
//        }
//    }
//
//
//
//    Row(
//        Modifier
//            .background(color = contentColor.value).animateContentSize()
//            .padding(8.dp)
//            .clickable {
//                expand.value = expand.value.not()
//            },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Icon(Icons.Filled.Menu, "menu")
//
//        Text(
//            text =  if (expand.value)
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
//            else
//                "Click Me!",
//            modifier = Modifier
////                .padding(start = 4.dp)
////                .absoluteOffset(x = contentPosition.value)
//
//        )
//    }


//    val expand = remember { mutableStateOf(false) }
//    val transition = updateTransition(expand.value)
//    val contentColor = transition.animateColor() { expanded ->
//        when (expanded) {
//            true -> Color.Green
//            false -> Color.Blue
//        }
//    }
//    val contentSize = transition.animateIntSize { expanded ->
//        when (expanded) {
//            true -> IntSize(100, 20)
//            false -> IntSize.Zero
//        }
//    }
//    val contentPosition = transition.animateDp { expanded ->
//        when (expanded) {
//            true -> 20.dp
//            false -> 0.dp
//        }
//    }
//    val iconColor = transition.animateColor { expanded ->
//        when (expanded) {
//            true -> Color.Black
//            false -> Color.White
//        }
//    }
//
//    FloatingActionButton(
//        modifier = modifier,
//        onClick = { expand.value = expand.value.not() },
//        content = {
//            Row(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Icon(
//                    Icons.Default.Add,
//                    contentDescription = null,
//                    tint = iconColor.value
//                )
//                Text(
//                    text = "getString",
//                    modifier = Modifier
//                        .padding(start = 4.dp)
//                        .absoluteOffset(x = contentPosition.value)
//                        .size(
//                            height = contentSize.value.height.dp,
//                            width = contentSize.value.width.dp
//                        )
//                )
//            }
//        },
//        backgroundColor = contentColor.value
//    )
}



@Composable
private fun AnimatedContentSizeExample() {
    val expand = remember { mutableStateOf(false) }

    val surfaceColor: Color by animateColorAsState(
        if (expand.value) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Card(elevation = 10.dp) {
        Box(modifier = Modifier
            .background(Color.Blue)
            .animateContentSize()
            .clickable { expand.value = expand.value.not() }) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = surfaceColor
                )

                if (expand.value) {
                    Text(
                        modifier = Modifier.padding(start =  8.dp),
                        text =
                        "Winter season pictures",
                        style = TextStyle(color = Color.White)
                    )
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Preview
@Composable
fun ppp() {
    AnimatedContentSizeExample()

}