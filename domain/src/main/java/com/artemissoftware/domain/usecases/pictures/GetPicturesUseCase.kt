package com.artemissoftware.domain.usecases.pictures

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.GalleryRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val profileDataStoreRepository: ProfileDataStoreRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke(galleryId: Int? = null) : Flow<Resource<List<Picture>>> = flow {

        val userProfile = profileDataStoreRepository.getUserProfile().first()
        val user = authenticationRepository.getUser().first()
        val favorites = userProfile.data[user?.email]

        emit(Resource.Loading())
        delay(500)

        val result = galleryRepository.getPictures(galleryId = galleryId, favorites = favorites)

        result.data?.let { pictures ->

            if (pictures.isEmpty()) {
                emit(Resource.Error(
                    message = NO_FAVORITE_PICTURES_AVAILABLE,
                    data = pictures
                ))
            } else {
                emit(Resource.Success(data = pictures))
            }

        } ?: kotlin.run {
            emit(Resource.Error(message = result.error.message))
        }

    }


    companion object{
        const val NO_FAVORITE_PICTURES_AVAILABLE = "No favorite pictures available"
        const val NO_PICTURES_AVAILABLE = "No pictures available"
        const val INEXISTENT_GALLERY = "Gallery does not exist"
    }
}