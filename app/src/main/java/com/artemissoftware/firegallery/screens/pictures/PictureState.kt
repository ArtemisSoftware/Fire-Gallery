package com.artemissoftware.firegallery.screens.pictures

import com.artemissoftware.domain.models.Picture

data class PictureState(
    val galleryName : String? = null,
    val galleryAuthor : String? = null,
    val pictures: List<Picture> = emptyList(),
    val favorites: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val showOptions: Boolean = false,
) {
    fun isFavorite(pictureId: String): Boolean {
        return favorites.contains(pictureId)
    }
}