package com.artemissoftware.data.repositories

import com.artemissoftware.data.errors.FireGalleryException
import com.artemissoftware.data.firebase.FireStoreCollection
import com.artemissoftware.data.firebase.FireStoreDocumentField
import com.artemissoftware.data.firebase.HandleFirebase
import com.artemissoftware.data.firebase.source.CloudStoreSource
import com.artemissoftware.data.firebase.entities.GalleryFso
import com.artemissoftware.data.firebase.entities.PictureFso
import com.artemissoftware.data.mappers.toFirebaseError
import com.artemissoftware.data.mappers.toGallery
import com.artemissoftware.data.mappers.toPicture
import com.artemissoftware.domain.FirebaseError
import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.repositories.GalleryRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val cloudStoreSource: CloudStoreSource
) : GalleryRepository {

    override suspend fun getGalleries(): FirebaseResponse<List<Gallery>> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{ cloudStoreSource.getDocuments(collectionName = FireStoreCollection.GALLERY) }

            return FirebaseResponse(data = response.map { document ->
                document.toObject<GalleryFso>()!!.toGallery()
            })

        } catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }

    }

    override suspend fun getPictures(galleryId: Int?, favorites: List<String>?): FirebaseResponse<List<Picture>>{

        return when{

            (galleryId != null && favorites == null) ->{ //Just gallery pictures
                getPicturesByGallery(galleryId)
            }

            (galleryId != null && favorites != null) ->{ //Just gallery pictures
                getPicturesByGallery(galleryId, favorites)
            }

            (galleryId == null && favorites != null) ->{ //just favorite pictures
                getFavoritePictures(favorites)
            }

            (galleryId != null && favorites != null) ->{ //just favorite pictures in a gallery
                FirebaseResponse(error = FirebaseError(message = "Query not supported"))
            }
            else->{ //error
                FirebaseResponse(error = FirebaseError(message = "Invalid query parameters"))
            }
        }
    }



    private suspend fun getPicturesByGallery(galleryId: Int, favorites: List<String> = emptyList()): FirebaseResponse<List<Picture>> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{
                cloudStoreSource.getPictures(galleryId)
            }

            return FirebaseResponse(data = response.map { document ->
                document.toObject<PictureFso>()!!.toPicture(favorites = favorites)
            })
        }
        catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }
    }


    private suspend fun getFavoritePictures(pictureIds: List<String>): FirebaseResponse<List<Picture>> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{
                cloudStoreSource.getDocumentItems(
                    collectionName = FireStoreCollection.PICTURES,
                    documentField = FireStoreDocumentField.ID,
                    id = pictureIds
                )
            }

            return FirebaseResponse(data = response.map { document ->
                document.toObject<PictureFso>()!!.toPicture(true)
            })

        }
        catch (ee: IllegalArgumentException){
            FirebaseResponse(error = ee.toFirebaseError())
        }
        catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }

    }



    override suspend fun getPicturesForTinder(numberOfImages: Int, favoriteImages: List<String>?, blackListedPictureIds : List<String>): FirebaseResponse<List<Picture>> {
        return try {
            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso> {
                cloudStoreSource.getPicturesForTinder(numberOfImages, favoriteImages?: emptyList(), blackListedPictureIds)
            }

            FirebaseResponse(data = response.map { document ->
                document.toObject<PictureFso>()!!.toPicture(false) //TODO: tenho que mudar isto, este false deve ser por omissão. COmo está fica confuso
            })
        } catch (ex : FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }
    }



    override suspend fun getPictureDetail(pictureId: String): FirebaseResponse<Picture> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{
                cloudStoreSource.getDocumentItems(
                    collectionName = FireStoreCollection.PICTURES,
                    documentField = FireStoreDocumentField.ID,
                    id = pictureId as Object
                )
            }

            val pictures = response.map { document ->
                document.toObject<PictureFso>()!!.toPicture(false) //TODO: isto é um problema
            }

            if(pictures.isEmpty()){
                FirebaseResponse(error = FirebaseError(message = "No pictures available"))
            }
            else {
                FirebaseResponse(data = pictures.first())
            }

        } catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }
    }


}