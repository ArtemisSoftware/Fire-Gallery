package com.artemissoftware.data.firebase.remoteconfig.models

data class UserValidationRco(
    val emailRegex: String,
    val usernameRegex: String,
    val passwordMaxLength: Int,
    val passwordMinLength: Int,
    val usernameMaxLength: Int,
    val usernameMinLength: Int
)
