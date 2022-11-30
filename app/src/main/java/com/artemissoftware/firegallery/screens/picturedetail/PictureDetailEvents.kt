package com.artemissoftware.firegallery.screens.picturedetail

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class PictureDetailEvents: FGBaseEvents() {
    data class FavoritePicture(val id: String, val isFavorite: Boolean): PictureDetailEvents()
    object PopBackStack: PictureDetailEvents()
}
