package com.artemissoftware.domain.repositories

import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture

interface GalleryRepository {

    suspend fun getGalleries(): FirebaseResponse<List<Gallery>>

    suspend fun getPicturesForTinder(numberOfImages : Int, favoriteImages: List<String>?, blackListedPictureIds: List<String>): FirebaseResponse<List<Picture>>

    suspend fun getPictures(galleryId: Int? = null, favorites: List<String>? = null): FirebaseResponse<List<Picture>>

    suspend fun getPictureDetail(pictureId: String): FirebaseResponse<Picture>
}