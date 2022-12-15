package com.artemissoftware.firegallery.screens.tindergallery.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.Purple200
import com.artemissoftware.common.theme.RedOrange
import com.artemissoftware.firegallery.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableNotification_final(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
){

    val density: Density = LocalDensity.current

    var widthText = remember { mutableStateOf(0.dp) }
    var widthIcon = remember { mutableStateOf(0.dp) }

    //var squareSize by remember { mutableStateOf(widthText) }
    var squareSize = widthText.value - 8.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    //val anchors = mapOf(0f to 0, -sizePx to -1)
    val anchors = mapOf(-sizePx to 0, 0f to -1)
    //val anchors = mapOf(0f to 0, -1f to -sizePx.toInt() )


    val corners =  RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 30.dp,
        bottomEnd = 30.dp,
        bottomStart = 0.dp,
    )


    var isAnimating by remember { mutableStateOf(true)}

    val shape = animateIntAsState(
        if (isAnimating) 0 else -sizePx.toInt(),
        animationSpec = TweenSpec(3500, 0)
    )

//    LaunchedEffect(key1 = true){
//
//        delay(700)
//
//        isAnimating = true
//
//        delay(5000)
//        squareSize = widthText.value - 8.dp
//        isAnimating = false
//
//    }



//    if(isAnimating) {

        TutorialNotificationCard(
            text = text,
            imageUrl = imageUrl,
            widthIcon = widthIcon,
            widthText = widthText,
            shape = shape
        )

//    }
//    else {
//
//        SwipeableNotificationCard(
//            text = text,
//            imageUrl = imageUrl,
//            widthIcon = widthIcon,
//            widthText = widthText
//        )
//
//    }

}


@Preview
@Composable
private fun SwipeableNotification_finalPreview() {
    SwipeableNotification_final(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}


//--------------------



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableNotification_finalForSure(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
){
    val coroutineScope = rememberCoroutineScope()

    var currentState = remember { mutableStateOf(Tutot.TUTORIAL) }
    var widthText = remember { mutableStateOf(0.dp) }
    var widthIcon = remember { mutableStateOf(0.dp) }

    var widthText_ = remember { mutableStateOf(0.dp) }
    var widthIcon_ = remember { mutableStateOf(0.dp) }
    val shape = animateIntAsState(
        0,
        animationSpec = TweenSpec(3500, 0)
    )



    when(currentState.value){
        Tutot.EXPAND -> {

        }
        Tutot.PREVIEW -> {

            Log.d("lolo", "widthText = " + widthText.value)

            TutorialNotificationCard(
                text = text,
                imageUrl = imageUrl,
                widthIcon = widthIcon,
                widthText = widthText,
                shape = shape
            )
        }
        Tutot.TUTORIAL -> {
            TutorialNotificationCard_OnHold(
                imageUrl = imageUrl,
                widthIcon = widthIcon_,
                widthText = widthText_
            )

            LaunchedEffect(key1 = true){

                delay(2500)

                currentState.value = Tutot.PREVIEW

            }
        }
    }

}





@Preview
@Composable
private fun SwipeableNotification_finalForSurePreview() {
    SwipeableNotification_finalForSure(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}












@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableNotificationCard(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
    widthIcon: MutableState<Dp>,
    widthText: MutableState<Dp>
) {

    var squareSize = widthText.value - 8.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    //val anchors = mapOf(0f to 0, -sizePx to -1)
    val anchors = mapOf(-sizePx to 0, 0f to -1)
    //val anchors = mapOf(0f to 0, -1f to -sizePx.toInt() )


    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    swipeableState.offset.value.roundToInt(),
                    0
                )
            }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.6f) },
                orientation = Orientation.Horizontal
            )

    ) {
        NotificationCard(
            text = text,
            imageUrl = imageUrl,
            widthIcon = widthIcon,
            widthText = widthText,
            endBorderColor = endBorderColor,
            startBorderColor = startBorderColor
        )
    }


}



@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun SwipeableNotificationCardPreview() {
    SwipeableNotificationCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png",
        widthText = mutableStateOf(100.dp),
        widthIcon = mutableStateOf(100.dp),
    )
}



private enum class Tutot {
    EXPAND,
    PREVIEW,
    TUTORIAL
}

@Composable
private fun TutorialNotificationCard_OnHold(
    modifier: Modifier = Modifier,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
    widthIcon: MutableState<Dp>,
    widthText: MutableState<Dp>
) {


    val defaultWidthRetracted = -16

    Box(
        modifier = modifier
            .offset {

                IntOffset(
                    defaultWidthRetracted,
                    //shape.value,
                    0
                )
            }

    ) {

        NotificationCard(
            text = "",
            imageUrl = imageUrl,
            widthIcon = widthIcon,
            widthText = widthText,
            endBorderColor = endBorderColor,
            startBorderColor = startBorderColor
        )
    }
}



@Composable
private fun TutorialNotificationCard(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
    widthIcon: MutableState<Dp>,
    widthText: MutableState<Dp>,
    shape: State<Int>
) {

//    var start = remember { mutableStateOf(true) }
//    var currentState = remember { mutableStateOf(Tutot.PREVIEW) }
//
//    LaunchedEffect(key1 = start.value) {
//        currentState.value =
//            if (start.value) Tutot.EXPAND else Tutot.PREVIEW
//    }
//    val transition = updateTransition(currentState.value, label = "")
//
//
//    var squareSize = widthText.value - 8.dp
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//
//
//    val optionHeight by animateIntAsState(
//        targetValue = if (currentState.value == Tutot.PREVIEW) -sizePx.toInt() else 0,
//        animationSpec = tween(
//            durationMillis = 2000, // duration
//            delayMillis = 2000, // delay before start animation
//            easing = FastOutSlowInEasing
//        ),
//        finishedListener = {
//            //currentState.value = Tutot.RETRACT
//            start.value = false
//        }
//    )





    var currentState = remember { mutableStateOf(Tutot.PREVIEW) }

    var start = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = start.value) {

        currentState.value = if (start.value) Tutot.EXPAND else Tutot.PREVIEW
    }

    var widthExpanded = widthText.value - 8.dp
    //--val defaultWidthRetracted = if(widthText.value > 0.dp) - with(LocalDensity.current) { widthText.value.toPx() -16 } else -16

    val defaultWidthRetracted = when {

        (currentState.value == Tutot.PREVIEW && widthText.value == 0.dp) -> {
            -16
        }
        (currentState.value == Tutot.PREVIEW && widthText.value > 0.dp) -> {
            Log.d("lolo", "wid__thText = " + widthText.value)
            -with(LocalDensity.current) { widthText.value.toPx() -16 }
        }
        else ->{ -16 }
    }


    val widthExpandedFinal = with(LocalDensity.current) { widthExpanded.toPx() }

    val optionHeight by animateIntAsState(
        targetValue = if (currentState.value == Tutot.PREVIEW) defaultWidthRetracted.toInt() else -widthExpandedFinal.toInt(),
        animationSpec = tween(
            durationMillis = 2000, // duration
            delayMillis = 2000, // delay before start animation
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            //currentState.value = Tutot.RETRACT
            //--start.value = false
        }
    )

    val ddd = if(start.value) optionHeight else defaultWidthRetracted.toInt()

    Box(
        modifier = modifier
            .offset {

                IntOffset(
                    //optionHeight,
                    defaultWidthRetracted.toInt(),
                    0
                )
            }

    ) {

        NotificationCard(
            text = text,
            imageUrl = imageUrl,
            widthIcon = widthIcon,
            widthText = widthText,
            endBorderColor = endBorderColor,
            startBorderColor = startBorderColor
        )
    }
}




@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun TutorialNotificationCardPreview() {
    TutorialNotificationCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png",
        widthText = mutableStateOf(0.dp),
        widthIcon = mutableStateOf(0.dp),
        shape = mutableStateOf(0),
    )
}




@Composable
private fun NotificationCard(
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200,
    density: Density = LocalDensity.current,
    widthIcon: MutableState<Dp>,
    widthText: MutableState<Dp>
){

    val corners =  RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 30.dp,
        bottomEnd = 30.dp,
        bottomStart = 0.dp,
    )

    Card(
        modifier = Modifier
            .border(
                width = (0.1).dp,
                brush = Brush.linearGradient(colors = listOf(startBorderColor, endBorderColor)),
                shape = corners
            )
            .shadow(clip = true, shape = corners, elevation = 3.dp),
        elevation = 24.dp,
        shape = corners
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.background(color = Color.Red)
                    .onGloballyPositioned { coordinates ->
                        widthText.value = with(density) { coordinates.size.width.toDp() }
                    }
            ) {

                FGText(
                    modifier = Modifier.padding(8.dp),
                    text = text
                )
            }

            Column(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        widthIcon.value = with(density) { coordinates.size.width.toDp() }
                    }
                    .padding(end = 16.dp)
            ) {

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .placeholder(R.drawable.ic_flame)
                        .size(Size.ORIGINAL)
                        .crossfade(500)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(
                            width = (0.1).dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    startBorderColor,
                                    endBorderColor
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                )

            }
        }

    }
}



@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun NotificationCardPreview() {
    NotificationCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png",
        widthText = mutableStateOf(100.dp),
        widthIcon = mutableStateOf(100.dp)
    )
}














































//
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun SwipeableNotification(
//    modifier: Modifier = Modifier,
//    text: String,
//    imageUrl: String,
//    endBorderColor: Color = RedOrange,
//    startBorderColor: Color = Purple200,
//){
//
//    val density: Density = LocalDensity.current
//
//    var widthText by remember { mutableStateOf(0.dp) }
//    var widthIcon by remember { mutableStateOf(0.dp) }
//
//    //var squareSize by remember { mutableStateOf(widthText) }
//    var squareSize = widthText - 8.dp
//
//    val swipeableState = rememberSwipeableState(0)
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//    //val anchors = mapOf(0f to 0, -sizePx to -1)
//    val anchors = mapOf(-sizePx to 0, 0f to -1)
//    //val anchors = mapOf(0f to 0, -1f to -sizePx.toInt() )
//
//
//    val corners =  RoundedCornerShape(
//        topStart = 0.dp,
//        topEnd = 30.dp,
//        bottomEnd = 30.dp,
//        bottomStart = 0.dp,
//    )
//
//
//    var isAnimating by remember { mutableStateOf(true)}
//
//    val shape = animateIntAsState(
//        if (isAnimating) 0 else -sizePx.toInt(),
//        animationSpec = TweenSpec(150, 0)
//    )
//
//    LaunchedEffect(key1 = true){
//
//        delay(700)
//
//        isAnimating = true
//
//        delay(1500)
//        squareSize = widthText - 8.dp
//        isAnimating = false
//
//    }
//
//
//
//    if(isAnimating == false) {
//        Box(
//            modifier = modifier
//                .offset {
//
//                    IntOffset(
//                        swipeableState.offset.value.roundToInt(),
//                        0
//                    )
//                }
//                .swipeable(
//                    state = swipeableState,
//                    anchors = anchors,
//                    thresholds = { _, _ -> FractionalThreshold(0.6f) },
//                    orientation = Orientation.Horizontal
//                )
//
//        ) {
//
//            Card(
//                modifier = Modifier
//                    .border(
//                        width = (0.1).dp,
//                        brush = Brush.linearGradient(
//                            colors = listOf(
//                                startBorderColor,
//                                endBorderColor
//                            )
//                        ),
//                        shape = corners
//                    ),
//                elevation = 24.dp,
//                shape = corners
//            ) {
//
//                Row(
//                    modifier = Modifier,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Column(
//                        modifier = Modifier
//                            .onGloballyPositioned { coordinates ->
//                                widthText = with(density) { coordinates.size.width.toDp() }
//                            }
//                    ) {
//
//                        FGText(
//                            modifier = Modifier.padding(8.dp),
//                            text = text
//                        )
//                    }
//
//                    Column(
//                        modifier = Modifier
//                            .onGloballyPositioned { coordinates ->
//                                widthIcon = with(density) { coordinates.size.width.toDp() }
//                            }
//                            .padding(end = 16.dp)
//                    ) {
//
//                        val painter = rememberAsyncImagePainter(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(imageUrl)
//                                .placeholder(R.drawable.ic_flame)
//                                .size(Size.ORIGINAL)
//                                .crossfade(500)
//                                .build()
//                        )
//
//                        Image(
//                            painter = painter,
//                            contentDescription = "",
//                            modifier = Modifier
//                                .size(24.dp)
//                                .clip(CircleShape)
//                                .border(
//                                    width = (0.1).dp,
//                                    brush = Brush.linearGradient(
//                                        colors = listOf(
//                                            startBorderColor,
//                                            endBorderColor
//                                        )
//                                    ),
//                                    shape = CircleShape
//                                ),
//                            contentScale = ContentScale.Crop,
//                        )
//
//                    }
//                }
//
//            }
//        }
//
//    }else {
//
//
//        Box(
//            modifier = modifier
//                .offset {
//
//                    IntOffset(
//                        shape.value,
//                        0
//                    )
//                }
//
//        ) {
//
//            Card(
//                modifier = Modifier
//                    .border(
//                        width = (0.1).dp,
//                        brush = Brush.linearGradient(
//                            colors = listOf(
//                                startBorderColor,
//                                endBorderColor
//                            )
//                        ),
//                        shape = corners
//                    )
//                    .shadow(clip = true, shape = corners, elevation = 3.dp),
//                elevation = 24.dp,
//                shape = corners
//            ) {
//
//                Row(
//                    modifier = Modifier,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Column(
//                        modifier = Modifier
//                            .onGloballyPositioned { coordinates ->
//                                widthText = with(density) { coordinates.size.width.toDp() }
//                            }
//                    ) {
//
//                        FGText(
//                            modifier = Modifier.padding(8.dp),
//                            text = text
//                        )
//                    }
//
//                    Column(
//                        modifier = Modifier
//                            .onGloballyPositioned { coordinates ->
//                                widthIcon = with(density) { coordinates.size.width.toDp() }
//                            }
//                            .padding(end = 16.dp)
//                    ) {
//
//                        val painter = rememberAsyncImagePainter(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(imageUrl)
//                                .placeholder(R.drawable.ic_flame)
//                                .size(Size.ORIGINAL)
//                                .crossfade(500)
//                                .build()
//                        )
//
//                        Image(
//                            painter = painter,
//                            contentDescription = "",
//                            modifier = Modifier
//                                .size(24.dp)
//                                .clip(CircleShape)
//                                .border(
//                                    width = (0.1).dp,
//                                    brush = Brush.linearGradient(
//                                        colors = listOf(
//                                            startBorderColor,
//                                            endBorderColor
//                                        )
//                                    ),
//                                    shape = CircleShape
//                                ),
//                            contentScale = ContentScale.Crop,
//                        )
//
//                    }
//                }
//
//            }
//        }
//
//    }
//
//}
//
//
//
//@Preview
//@Composable
//fun Swipe() {
//    SwipeableNotification(text = "Winter season pictures", imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png")
//}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun SwipeableNotification_Demo(
//    modifier: Modifier = Modifier,
//    text: String,
//    imageUrl: String,
//    endBorderColor: Color = RedOrange,
//    startBorderColor: Color = Purple200,
//){
//
//    val density: Density = LocalDensity.current
//
//    var widthText by remember { mutableStateOf(0.dp) }
//    var widthIcon by remember { mutableStateOf(0.dp) }
//
//    //var squareSize by remember { mutableStateOf(widthText) }
//    var squareSize = widthText - 8.dp
//
//    val swipeableState = rememberSwipeableState(0)
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//    val anchors = mapOf(0f to 0, -sizePx to -1)
//    //val anchors = mapOf(0f to 0, -1f to -sizePx.toInt() )
//
//
//    val corners =  RoundedCornerShape(
//        topStart = 0.dp,
//        topEnd = 30.dp,
//        bottomEnd = 30.dp,
//        bottomStart = 0.dp,
//    )
//
//
//    var isAnimating by remember { mutableStateOf(true)}
//
//    val shape = animateIntAsState(
//        if (isAnimating) 0 else -sizePx.toInt(),
//        animationSpec = TweenSpec(150, 0)
//    )
//
//
//
//
//
//
//
//
//
//
//    LaunchedEffect(key1 = true){
//
//        delay(700)
//
//        isAnimating = true
//
//        delay(1500)
//        squareSize = widthText - 8.dp
//        isAnimating = false
//
//    }
//
//    Box(
//        modifier = modifier
//            .offset {
//
//                IntOffset(
//                    shape.value,
//                    0
//                )
//            }
//
//    ) {
//
//        Card(
//            modifier = Modifier
//                .border(
//                    width = (0.1).dp,
//                    brush = Brush.linearGradient(colors = listOf(startBorderColor, endBorderColor)),
//                    shape = corners
//                ),
//            elevation = 24.dp,
//            shape = corners
//        ) {
//
//            Row(
//                modifier = Modifier,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Column(
//                    modifier = Modifier
//                        .onGloballyPositioned { coordinates ->
//                            widthText = with(density) { coordinates.size.width.toDp() }
//                        }
//                ) {
//
//                    FGText(
//                        modifier = Modifier.padding(8.dp),
//                        text = text
//                    )
//                }
//
//                Column(
//                    modifier = Modifier
//                        .onGloballyPositioned { coordinates ->
//                            widthIcon = with(density) { coordinates.size.width.toDp() }
//                        }
//                        .padding(end = 16.dp)
//                ) {
//
//                    val painter = rememberAsyncImagePainter(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(imageUrl)
//                            .placeholder(R.drawable.ic_flame)
//                            .size(Size.ORIGINAL)
//                            .crossfade(500)
//                            .build()
//                    )
//
//                    Image(
//                        painter = painter,
//                        contentDescription = "",
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(CircleShape)
//                            .border(
//                                width = (0.1).dp,
//                                brush = Brush.linearGradient(
//                                    colors = listOf(
//                                        startBorderColor,
//                                        endBorderColor
//                                    )
//                                ),
//                                shape = CircleShape
//                            ),
//                        contentScale = ContentScale.Crop,
//                    )
//
//                }
//            }
//
//        }
//    }
//
//
//
//}
//
//
//
//@Preview
//@Composable
//fun Demo() {
//    SwipeableNotification_Demo(text = "Winter season pictures", imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png")
//
//
//
//
//}
//
//
//
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun SwipeFinal(
//    modifier: Modifier = Modifier,
//    text: String,
//    imageUrl: String,
//    endBorderColor: Color = RedOrange,
//    startBorderColor: Color = Purple200,
//){
//
//    val density: Density = LocalDensity.current
//    var widthText by remember { mutableStateOf(0.dp) }
//    var widthIcon by remember { mutableStateOf(0.dp) }
//    var squareSize by remember { mutableStateOf(widthText) }
//    //val squareSize = widthText //- 8.dp
//
//    val swipeableState = rememberSwipeableState(0)
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//    val anchors = mapOf(0f to 0, -sizePx to -1)
//
//
//
//    var isSelected by remember { mutableStateOf(false)}
//
//    val shape = animateIntAsState(
//        if (isSelected) 0 else -sizePx.toInt(),
//        animationSpec = TweenSpec(150, 0)
//    )
//
//
//    LaunchedEffect(key1 = true){
//
//        delay(700)
//
//        isSelected = true
//
//        delay(1500)
//        squareSize = widthText - 8.dp
//        isSelected = false
//
//    }
//
//
//    Box(
//        modifier = modifier
//            .offset {
//
//                IntOffset(
//                    shape.value,
//                    //offSet,
//                    //if(!isSelected) swipeableState.offset.value.roundToInt() else shape.value,//swipeableState.offset.value.roundToInt(),
//                    0
//                )
//            }
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.6f) },
//                orientation = Orientation.Horizontal
//            )
//
//    ) {
//
//
////        NoteCard(
////            text = text,
////            imageUrl = imageUrl,
////            endBorderColor = endBorderColor,
////            startBorderColor = startBorderColor,
////            widthIcon = widthIcon,
////            widthText = widthText
////        )
////
//
//    }
//}
//
//
//
//@Preview
//@Composable
//fun ppp() {
//    SwipeFinal(text = "Winter season pictures", imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png")
//}



@Composable
fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}


@Preview
@Composable
fun ppp() {
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
        Text("ddd = $measuredWidth")
    }

}

