package com.artemissoftware.data.mappers

import com.artemissoftware.data.firebase.entities.GalleryFso
import com.artemissoftware.data.firebase.entities.PictureFso
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture
import org.junit.Assert.*

import org.junit.Test

class GalleryMapperTest {

    @Test
    fun `map galleryFso to gallery`() {

        val galleryFso = GalleryFso(
            id = 1,
            name = "New Gallery",
            imageUrl = "http://www.images.com/3534.jpg"
        )

        val gallery = Gallery(
            id = 1 ,
            name = "New Gallery",
            imageUrl = "http://www.images.com/3534.jpg"
        )

        assertEquals(gallery, galleryFso.toGallery())
    }

    @Test
    fun `map PictureFso to Picture`() {

        val picture = Picture(
            id = "AA",
            title ="The picture",
            author ="The author",
            tags = listOf("AA", "BB", "CC"),
            imageUrl = "http://www.images.com/3534.jpg"
        )

        assertEquals(picture, getPictureFso().toPicture())
    }

    @Test
    fun `map PictureFso to Picture with list of favorites that contains favorites`() {

        val picture = Picture(
            id = "AA",
            isFavorite = true,
            title ="The picture",
            author ="The author",
            tags = listOf("AA", "BB", "CC"),
            imageUrl = "http://www.images.com/3534.jpg"
        )

        assertEquals(picture, getPictureFso().toPicture(favorites = listOf("AA")))
    }

    @Test
    fun `map PictureFso to Picture with list of favorites that does not contain favorites`() {

        val picture = Picture(
            id = "AA",
            isFavorite = false,
            title ="The picture",
            author ="The author",
            tags = listOf("AA", "BB", "CC"),
            imageUrl = "http://www.images.com/3534.jpg"
        )

        assertEquals(picture, getPictureFso().toPicture(favorites = listOf("CC")))
    }

    private fun getPictureFso(): PictureFso{
        return PictureFso(
            id = "AA",
            author = "The author",
            imageUrl = "http://www.images.com/3534.jpg",
            tags = listOf("AA", "BB", "CC"),
            title = "The picture"
        )
    }

}