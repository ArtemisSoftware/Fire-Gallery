package com.artemissoftware.firegallery.screens.picturedetail

import com.artemissoftware.domain.models.Picture

data class PictureDetailState(
    val picture: Picture? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false
){
    fun showToolbar() = picture != null
}
