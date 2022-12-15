package com.artemissoftware.firegallery.screens.tindergallery.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.common.theme.Purple200
import com.artemissoftware.common.theme.RedOrange
import com.artemissoftware.firegallery.R


@Composable
fun NotificationSideCard(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    endBorderColor: Color = RedOrange,
    startBorderColor: Color = Purple200
){

    val corners =  RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 30.dp,
        bottomEnd = 30.dp,
        bottomStart = 0.dp,
    )

    Card(
        modifier = modifier
            .border(
                width = (0.4).dp,
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
                modifier = Modifier
            ) {

                FGText(
                    style = FGStyle.TextAlbertSansBold,
                    modifier = Modifier.padding(8.dp),
                    text = text
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
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
                            width = (0.4).dp,
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


@Preview
@Composable
private fun NotificationSideCardPreview() {
    NotificationSideCard(
        text = "Winter season pictures",
        imageUrl = "https://cdn-icons-png.flaticon.com/128/2336/2336319.png"
    )
}
