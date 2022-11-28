package com.artemissoftware.firegallery.screens.picturedetail

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class PictureDetailEvents: FGBaseEvents() {
    data class GetPicture(val id: String): PictureDetailEvents()
    data class FavoritePicture(val id: String, val isFavorite: Boolean): PictureDetailEvents()
    object GetUser: PictureDetailEvents()
    object PopBackStack: PictureDetailEvents()
}
