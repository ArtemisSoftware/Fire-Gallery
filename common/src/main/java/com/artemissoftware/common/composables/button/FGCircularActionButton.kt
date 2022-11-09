package com.artemissoftware.common.composables.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FGCircularActionButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color = Color(31, 233, 184),
    backgroundColor : Color = Color.Transparent,
    buttonSize : Dp = 48.dp,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = backgroundColor,
        modifier = modifier
            .border(width = 1.dp, color = color, shape = CircleShape)
            .size(buttonSize)
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            },
    ) {

        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = imageVector,
            contentDescription = "", tint = color,
        )
    }
}


@Preview
@Composable
fun FGCircularActionButtonPreview() {
    FGCircularActionButton(imageVector = Icons.Default.Favorite, onClick = {})
}