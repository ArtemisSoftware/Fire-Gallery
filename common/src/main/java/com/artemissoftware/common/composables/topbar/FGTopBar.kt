package com.artemissoftware.common.composables.topbar

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGCircularButton
import com.artemissoftware.common.composables.button.FGSquareIconButton
import com.artemissoftware.common.composables.text.FGOutlinedText
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.FGStyle


@Composable
fun FGTopBar(
    modifier: Modifier = Modifier.padding(top = 0.dp),
    backgroundColor: Color = Color.Transparent,
    title: String? = null,
    subTitle: String? = null,
    isVisible: Boolean = false,
    onNavigationClick: () -> Unit,
    optionComposable: @Composable() (BoxScope.() -> Unit)? = null,

//    @DrawableRes navigationIconId: Int? = R.drawable.ic_arrow_left,
//    @DrawableRes optionIconId: Int? = null,
//    onOptionClick: (() -> Unit) = {},
) {

    val expandedHeight = 56.dp
    val animationTime = 300

    var currentState = remember { mutableStateOf(FGCollapsedState.COLLAPSED) }
    LaunchedEffect(key1 = isVisible) {
        currentState.value =
            if (isVisible) FGCollapsedState.EXPANDED else FGCollapsedState.COLLAPSED
    }
    val transition = updateTransition(currentState.value, label = "")

    val height by transition.animateDp(label = "") {
        when (it) {
            FGCollapsedState.COLLAPSED -> -expandedHeight
            FGCollapsedState.EXPANDED -> 0.dp
        }
    }

    val alpha by transition.animateFloat(label = "") {
        when (it) {
            FGCollapsedState.COLLAPSED -> 0F
            FGCollapsedState.EXPANDED -> 1F
        }
    }

    val optionHeight: Dp by animateDpAsState(
        targetValue = if (currentState.value == FGCollapsedState.EXPANDED) 0.dp else (-56).dp,
        animationSpec = tween(
            durationMillis = animationTime, // duration
            delayMillis = 200, // delay before start animation
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            // disable the button
            //isEnabled.value = true
        }
    )


    AppBar(
        backgroundColor = backgroundColor,
        modifier = Modifier
    ) {

        TopBar(
            modifier = Modifier
                .fillMaxHeight(),
            animationTime = animationTime,
            title = title,
            subTitle = subTitle,
            height = height,
            alpha = alpha,
            optionHeight = optionHeight,
            currentState = currentState,
            onNavigationClick = onNavigationClick,
            optionComposable = optionComposable,
        )
    }
}


@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    subTitle: String? = null
) {

    val textSize = if(subTitle == null) 50F else 40F

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        title?.let {

            FGOutlinedText(text = it, textSize = textSize)

        }

        subTitle?.let {
            FGText(
                text = it,
                style = FGStyle.TextAlbertSansMedium,
                textAlign = TextAlign.Start
            )
        }
    }

}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    animationTime: Int = 300,
    height: Dp = 56.dp,
    alpha: Float = 1F,
    optionHeight: Dp = 56.dp,
    currentState: MutableState<FGCollapsedState>,
    title: String? = null,
    subTitle: String? = null,
    onNavigationClick: () -> Unit,
    optionComposable: @Composable() (BoxScope.() -> Unit)? = null,
){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(
            visible = isVisible(currentState.value),
            enter = slideInHorizontally(durationMillis = animationTime),
            exit = slideOutLeft(),

        ) {

            FGSquareIconButton(
                imageVector = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier,
                buttonSize = 40.dp,
                onClick = {
                    currentState.value = FGCollapsedState.COLLAPSED
                    onNavigationClick.invoke()
                }
            )
        }

        TitleSection(
            modifier = Modifier
                .weight(0.5F)
                .offset(y = height)
                .alpha(alpha),
            title = title,
            subTitle = subTitle
        )


        Box(modifier = Modifier
            .weight(0.1F)
            .offset(y = optionHeight)
        ) {

            optionComposable?.let {
                it()
            }

        }

    }

}



@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = backgroundColor,
        elevation = 0.dp,
        shape = RectangleShape,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            content = content
        )
    }
}




@Stable
@ExperimentalAnimationApi
private fun slideInHorizontally(durationMillis: Int = 300): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -300 }, // it == fullWidth
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = LinearEasing
        )
    )
}

@Stable
@ExperimentalAnimationApi
private fun slideOutLeft(durationMillis: Int = 300): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(durationMillis)
    )
}

private fun isVisible (currentState: FGCollapsedState): Boolean {
    return currentState == FGCollapsedState.EXPANDED
}






@Preview(showBackground = true)
@Composable
private fun TitleSectionPreview() {

    TitleSection(
        title = "I am the title",
        subTitle = "You are my subtitle",
    )
}




@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {

    TopBar(currentState = mutableStateOf(FGCollapsedState.EXPANDED), onNavigationClick = {  })
}


@Preview(showBackground = true)
@Composable
private fun FGTopBar_1_Preview() {
    FGTopBar(
        backgroundColor = Color.Red,
        title = "I am the title",
        subTitle = "You are my subtitle",
        onNavigationClick = {},
        optionComposable = {
            FGCircularButton(
                imageVector = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier,
                onClick = { }
            )

        },
        isVisible = false
    )
}

@Preview(showBackground = true)
@Composable
private fun FGTopBar_2_Preview() {
    FGTopBar(
        backgroundColor = Color.Yellow,
        title = "I am the title",
        subTitle = "You are my subtitle",
        onNavigationClick = {},
        optionComposable = {

            FGCircularButton(
                imageVector = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier,
                onClick = { }
            )
        },
        isVisible = true
    )
}