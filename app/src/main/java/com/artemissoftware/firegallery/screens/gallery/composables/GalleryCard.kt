package com.artemissoftware.firegallery.screens.gallery.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.firegallery.R

@Composable
fun GalleryCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(text = "AB CDE", fontWeight = FontWeight.W700)
                Text(text = "+0 12345678")
                Text(text = "XYZ city", fontWeight = FontWeight.W300)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipSurfacePreview() {
    GalleryCard()
}
