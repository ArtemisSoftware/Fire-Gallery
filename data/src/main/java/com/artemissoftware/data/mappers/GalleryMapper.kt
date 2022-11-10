package com.artemissoftware.data.mappers

import com.artemissoftware.data.firebase.entities.GalleryFso
import com.artemissoftware.data.firebase.entities.PictureFso
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture

fun GalleryFso.toGallery(): Gallery {

    return Gallery(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}


fun PictureFso.toPicture(favorites: List<String>): Picture {

    return this.toPicture(
        isFavorite = favorites.contains(id)
    )
}

fun PictureFso.toPicture(isFavorite: Boolean = false): Picture {

    return Picture(
        id = id,
        author = author,
        imageUrl = imageUrl,
        tags = tags,
        title = title,
        isFavorite = isFavorite
    )
}