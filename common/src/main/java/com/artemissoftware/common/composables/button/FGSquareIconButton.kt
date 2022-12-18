package com.artemissoftware.common.composables.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.theme.SquareCornersShape
import com.artemissoftware.common.theme.PlateGrey

@Composable
fun FGSquareIconButton(
    imageVector: ImageVector,
    iconColor: Color = Color.LightGray,
    backGroundColor: Color = PlateGrey,
    modifier: Modifier = Modifier,
    buttonSize : Dp = 48.dp,
    onClick: () -> Unit = {}
) {

    Surface(
        shape = SquareCornersShape,
        color = backGroundColor,
        modifier = Modifier
            .size(buttonSize)
            .clip(SquareCornersShape)
            .clickable {
                onClick.invoke()
            },
        elevation = 0.dp, // play with the elevation values
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = iconColor,
        )
    }
}


@Preview
@Composable
private fun FGSquareIconButtonPreview() {


    FGSquareIconButton(
        imageVector = Icons.Default.Accessibility,
        backGroundColor = Color.Red,
        iconColor = Color.Yellow
    )

}
