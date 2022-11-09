package com.artemissoftware.domain.models

data class UserFavoriteImages(val data: HashMap<String, List<String>>){

    companion object{

        val mockUserFavoriteImages : UserFavoriteImages = UserFavoriteImages(hashMapOf("bb@aa.com" to listOf("1", "2", "3"), "b@b.com" to listOf("xs"), "c@c.com" to listOf("xdd")))

    }

}
