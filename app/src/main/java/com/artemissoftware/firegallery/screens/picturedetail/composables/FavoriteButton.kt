package com.artemissoftware.firegallery.screens.picturedetail.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.animations.FGPulsatingAnimation
import com.artemissoftware.common.composables.animations.models.PulsatingType

@Composable
fun FavoriteButton(
    pulsatingType: PulsatingType,
    modifier: Modifier = Modifier,
    onClickToFavorite: () -> Unit = {},
    onClickToRemoverFavorite: () -> Unit = {},
    isFavorite: Boolean
) {

    Box(modifier = modifier) {

        if (isFavorite) {

            FGPulsatingAnimation(
                pulsatingType = pulsatingType
            ) {

                IconButton(
                    onClick = {
                        onClickToRemoverFavorite()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Red
                    )
                }
            }
        } else {

            IconButton(
                onClick = {
                    onClickToFavorite()
                },
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Favorite,
                    contentDescription = "",
                    modifier = Modifier.size(50.dp),
                    tint = Color.LightGray
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonPreview() {

    FavoriteButton(
        pulsatingType = PulsatingType.INFINITE,
        modifier = Modifier,
        isFavorite = false
    )
}