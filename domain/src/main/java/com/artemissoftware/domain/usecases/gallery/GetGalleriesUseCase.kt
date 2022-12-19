package com.artemissoftware.domain.usecases.gallery

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.repositories.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetGalleriesUseCase @Inject constructor(private val galleryRepository: GalleryRepository) {

    operator fun invoke(): Flow<Resource<List<Gallery>>> = flow {

        emit(Resource.Loading())

        val result = galleryRepository.getGalleries()

        result.data?.let { galleries->
            emit(Resource.Success(data = galleries))
        } ?: kotlin.run {
            emit(Resource.Error(message = result.error.message))
        }

    }

}