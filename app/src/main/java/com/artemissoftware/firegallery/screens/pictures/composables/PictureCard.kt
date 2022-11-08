package com.artemissoftware.firegallery.screens.pictures.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.common.composables.FGCard
import com.artemissoftware.common.composables.animations.models.PulsatingType
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.picturedetail.composables.FavoriteButton


@Composable
fun PictureCard(
    isFavorite: Boolean = false,
    picture: Picture,
    onFavoriteClick: (String) -> Unit = {},
    onClick: (String) -> Unit,
) {

    PictureContent(
        addFavoriteButton = true,
        isFavorite = isFavorite,
        picture = picture,
        onClick = onClick,
        onFavoriteClick = onFavoriteClick
    )
}

@Composable
private fun PictureContent(
    addFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    picture: Picture,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit = {},
) {

    FGCard(
        onClick =  { onClick(picture.id) }
    ) {

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picture.imageUrl)
                .size(Size.ORIGINAL)
                .crossfade(500)
                .build()
        )

        Box {

            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            if (addFavoriteButton) {

                FavoriteButton(
                    pulsatingType = PulsatingType.LIMITED,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    onClickToFavorite = {
                        onFavoriteClick.invoke(picture.id)
                    },
                    onClickToRemoverFavorite = {
                        onFavoriteClick.invoke(picture.id)
                    },
                    isFavorite = isFavorite,
                )
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
private fun PictureCardPreview() {
    PictureCard(
        picture = Picture.picturesMockList[0],
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoriteCardPreview() {
    PictureCard(
        isFavorite = true,
        picture = Picture.picturesMockList[0],
        onFavoriteClick = {},
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PictureContentPreview() {

    PictureContent(picture = Picture.picturesMockList[0], isFavorite = true, onClick = {})
}