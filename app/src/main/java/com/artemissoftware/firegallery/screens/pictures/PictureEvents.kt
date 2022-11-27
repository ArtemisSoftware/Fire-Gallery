package com.artemissoftware.firegallery.screens.pictures

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class PictureEvents: FGBaseEvents() {
    data class GetPictures(val galleryId: Int): PictureEvents()
    data class GoToPictureDetail(val pictureId: String): PictureEvents()
    object PopBackStack: PictureEvents()
}
