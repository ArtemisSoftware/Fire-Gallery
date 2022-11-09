package com.artemissoftware.firegallery.screens.tindergallery

import com.artemissoftware.domain.models.Picture

data class TinderGalleyState(
    val isLoading : Boolean = false,
    val pictures : List<Picture> = emptyList(),
    val currentIndex : Int = 0,
) {


    fun isInTheLastPicture() : Boolean {
        return currentIndex == 0
    }

    fun getCurrentPicture() : Picture? {
        return pictures.reversed().getOrNull(currentIndex)
    }

    fun showAddMoreButton() = pictures.isEmpty() && !isLoading
}