package com.artemissoftware.firegallery.screens.picturedetail.models

import com.artemissoftware.common.models.Chip

data class PictureUI(
    val id: String,
    val title: String,
    val author: String,
    val tags: List<Chip> = emptyList()
)
