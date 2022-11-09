package com.artemissoftware.domain.repositories

import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.models.UserFavoriteImages

interface GalleryRepository {

    suspend fun getGalleries(): FirebaseResponse<List<Gallery>>

    suspend fun getPicturesForTinder(numberOfImages : Int, favoriteImages: List<String>?, blackListedPictureIds: List<String>): FirebaseResponse<List<Picture>>

    suspend fun getPictures(galleryId: Int, favorites: List<String> = emptyList()): List<Picture>
    suspend fun getFavoritePictures(pictureIds: List<String>): FirebaseResponse<List<Picture>>

    suspend fun getPictureDetail(pictureId: String): FirebaseResponse<Picture>
}