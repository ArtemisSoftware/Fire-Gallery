package com.artemissoftware.domain.usecases.pictures

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.GalleryRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPicturesUseCase_ @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val profileDataStoreRepository: ProfileDataStoreRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke(galleryId: Int? = null, onlyFavorites: Boolean = false) : Flow<Resource<List<Picture>>> = flow {

        val userProfile = profileDataStoreRepository.getUserProfile().first()
        val user = authenticationRepository.getUser().first()
        val favorites = userProfile.data[user?.email] ?: emptyList()



        when{

            (galleryId != null && !onlyFavorites) ->{ //Just gallery pictures

                getGalleryPictures(galleryId, favorites)

            }

            (galleryId == null && onlyFavorites) ->{ //just favorite pictures

                getFavoritePictures(favorites)

            }

            (galleryId != null && onlyFavorites == true) ->{ //just favorite pictures in a gallery



            }


            else->{ //error

            }


        }

    }

    private suspend fun FlowCollector<Resource<List<Picture>>>.getFavoritePictures(favorites: List<String>) {

        var resource: Resource<List<Picture>> = Resource.Error(message = NO_FAVORITE_PICTURES_AVAILABLE)

        if(favorites.isNotEmpty()){

            val result = galleryRepository.getFavoritePictures(favorites)

            result.data?.let { pictures ->

                resource = if (pictures.isEmpty()) {
                    Resource.Error(
                        message = NO_FAVORITE_PICTURES_AVAILABLE,
                        data = pictures
                    )
                } else {
                    Resource.Success(data = pictures)
                }

            } ?: kotlin.run {
                resource = Resource.Error(message = result.error.message)
            }
        }

        emit(resource)
    }

    private suspend fun FlowCollector<Resource<List<Picture>>>.getGalleryPictures(
        galleryId: Int,
        favorites: List<String>
    ) {
        emit(Resource.Loading())

        val pictures = galleryRepository.getPictures(galleryId = galleryId, favorites)

        if (pictures.isEmpty()) {
            emit(
                Resource.Error(
                    message = NO_PICTURES_AVAILABLE,
                    data = pictures
                )
            )
        } else {
            emit(Resource.Success(data = pictures))
        }
    }


    companion object{
        const val NO_FAVORITE_PICTURES_AVAILABLE = "No favorite pictures available"
        const val NO_PICTURES_AVAILABLE = "No pictures available"
        const val INEXISTENT_GALLERY = "Gallery does not exist"
    }
}