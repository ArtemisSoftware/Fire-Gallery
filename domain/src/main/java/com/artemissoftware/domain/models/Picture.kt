package com.artemissoftware.domain.models

import com.artemissoftware.domain.models.configurations.ChipColorConfig

data class Picture(
    val id: String,
    val title: String,
    val isFavorite: Boolean = false,
    val author: String,
    val imageUrl: String,
    val tags: List<String> = emptyList(),
    var chipColorConfig: ChipColorConfig? = null
){

    companion object{

        val picturesMockList = listOf(
            Picture(id = "1", title ="Image 1", author ="First author", tags = listOf("1", "two ways"), imageUrl = "https://encenasaudemental.com/wp-content/uploads/2014/09/artemis.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "2", title ="Image 1", author ="First author", imageUrl = "https://unebrasil.org/wp-content/uploads/2020/02/Artemis.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "3", title ="Image 1", author ="First author", imageUrl = "https://4.bp.blogspot.com/_bRN4lXgrpG4/Sw0vgFY8qyI/AAAAAAAAA1A/Xs7chsD73-o/s1600/artemis2.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "4", title ="Image 1", author ="First author", imageUrl = "https://ih1.redbubble.net/image.2336478000.4145/flat,128x128,075,t-pad,128x128,f8f8f8.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "1", title ="Image 1", author ="First author", imageUrl = "https://encenasaudemental.com/wp-content/uploads/2014/09/artemis.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "2", title ="Image 1", author ="First author", imageUrl = "https://unebrasil.org/wp-content/uploads/2020/02/Artemis.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "3", title ="Image 1", author ="First author", imageUrl = "https://4.bp.blogspot.com/_bRN4lXgrpG4/Sw0vgFY8qyI/AAAAAAAAA1A/Xs7chsD73-o/s1600/artemis2.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
            Picture(id = "4", title ="Image 1", author ="First author", imageUrl = "https://ih1.redbubble.net/image.2336478000.4145/flat,128x128,075,t-pad,128x128,f8f8f8.jpg", chipColorConfig = ChipColorConfig.mockChipColorConfig),
        )

    }


}
