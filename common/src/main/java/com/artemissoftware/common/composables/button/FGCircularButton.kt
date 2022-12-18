package com.artemissoftware.common.composables.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.R
import com.artemissoftware.common.theme.LightBlue
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun FGCircularButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    buttonSize : Dp = 48.dp,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = LightBlue),
        modifier = modifier
            .clip(CircleShape)
            .size(buttonSize)
            .clickable {
                onClick.invoke()
        },
        elevation = 4.dp, // play with the elevation values
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = "", tint = color,
        )
    }
}

@Composable
fun FGCircularButton(
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    contentScale: ContentScale = ContentScale.Fit,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = CircleShape,
        modifier = modifier.clip(CircleShape).size(48.dp),
        elevation = 4.dp, // play with the elevation values
    ) {



        Image(
            painter = painterResource(id = resId),
            contentDescription = null, // decorative
            contentScale = contentScale,
            colorFilter  = ColorFilter.tint(color),

            )
    }
}

@Preview
@Composable
private fun FGCircularButtonPreview() {

    Column {

        FGCircularButton(
            imageVector = Icons.Default.KeyboardArrowLeft,
            modifier = Modifier
                //.align(Alignment.TopEnd)
                .padding(16.dp)
        ){}


        FGCircularButton(
            resId = R.drawable.ic_android,
        ){}
    }


}



@Preview
@Composable
private fun FGCircularButton_1_Preview() {


    //Row(Modifier.fillMaxWidth().height(100.dp)) {
        // Creating a Canvas to draw a Circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawCircle(
                color = Color(0xff0f9d58),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension/2,
                style = Stroke(4F)
            )
        }
    //}

}
