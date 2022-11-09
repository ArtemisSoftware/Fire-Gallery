package com.artemissoftware.firegallery.screens.favorites

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class FavoriteEvents: FGBaseEvents(){

    data class Remove(val pictureId: String): FavoriteEvents()
    data class GoToPictureDetail(val pictureId: String) : FavoriteEvents()
}
