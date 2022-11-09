package com.artemissoftware.domain.usecases.tinder

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.GalleryRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPicturesForTinderUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val profileDataStoreRepository: ProfileDataStoreRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke(numberOfImages : Int = DEFAULT_NUMBER_OF_IMAGES, blackListedPictures : List<Picture> = emptyList()): Flow<Resource<List<Picture>>> = flow {
        emit(Resource.Loading())
        val userProfile = profileDataStoreRepository.getUserProfile().first()
        val user = authenticationRepository.getUser().first()
        val alreadyFavoriteImages = userProfile.data[user?.email]

        val blackListedPicturesIds = blackListedPictures.map { it.id }
        val picturesResponse = galleryRepository.getPicturesForTinder(numberOfImages = numberOfImages, favoriteImages = alreadyFavoriteImages, blackListedPictureIds = blackListedPicturesIds)

        picturesResponse.data?.let { pictures ->
            if(pictures.isEmpty()){
                emit(Resource.Error(message = NO_PICTURES_AVAILABLE))
            }
            else{
                emit(Resource.Success(pictures))
            }
        }?: run {
            emit(Resource.Error(message = picturesResponse.error.message))
        }



    }

    companion object{
        const val DEFAULT_NUMBER_OF_IMAGES = 3
        const val NO_PICTURES_AVAILABLE = "No pictures available"
    }
}