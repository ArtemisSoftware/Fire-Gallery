package com.artemissoftware.firegallery.screens.gallery

import com.artemissoftware.firegallery.screens.gallery.models.GalleryUI
import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class GalleryEvents: FGBaseEvents() {

    data class GoToPictures(val galleryUI: GalleryUI) : GalleryEvents()
}