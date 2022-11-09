package com.artemissoftware.firegallery.screens.tindergallery

import com.artemissoftware.common.models.SwipeResult
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class TinderGalleryEvents : FGBaseEvents() {

    object FetchMorePictures : TinderGalleryEvents()

    class GoToNextPicture(val action : SwipeResult) : TinderGalleryEvents()
}