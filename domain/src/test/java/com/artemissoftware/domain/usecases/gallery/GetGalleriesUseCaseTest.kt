package com.artemissoftware.domain.usecases.gallery

import BaseUseCaseTest
import com.artemissoftware.domain.FirebaseError
import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.repositories.GalleryRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetGalleriesUseCaseTest: BaseUseCaseTest() {

    private lateinit var getGalleriesUseCase: GetGalleriesUseCase

    private lateinit var galleryRepository: GalleryRepository

    @Before
    fun setUp() {
        galleryRepository = mock()
        getGalleriesUseCase = GetGalleriesUseCase(galleryRepository)
    }

    @Test
    fun `get galleries return success`() = runBlockingTest {

        val galleries = listOf(
            Gallery(id = 1 , name = "Terminator", imageUrl = "https://i2-prod.mirror.co.uk/incoming/article8771720.ece/ALTERNATES/s1200b/The-Terminator.jpg"),
            Gallery(id = 2 , name = "Xenomorph", imageUrl = "https://img.cdn-pictorem.com/uploads/collection/O/OT2LAH5PCS/900_Morteza-Golpoor_image00025_00016.jpg")
        )

        whenever(galleryRepository.getGalleries()).thenReturn(
            FirebaseResponse(data = galleries)
        )

        val emissions = getGalleriesUseCase().toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Success)

        verify(galleryRepository, times(1)).getGalleries()
    }


    @Test
    fun `get galleries return failure`() = runBlockingTest {

        whenever(galleryRepository.getGalleries()).thenReturn(
            FirebaseResponse(error = FirebaseError(message = "No galleries available"))
        )

        val emissions = getGalleriesUseCase().toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Error)

        verify(galleryRepository, times(1)).getGalleries()
    }
}