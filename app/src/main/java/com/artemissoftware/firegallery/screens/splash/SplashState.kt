package com.artemissoftware.firegallery.screens.splash

data class SplashState(
    var dataLoaded: Boolean = false,
    var animationConcluded: Boolean = false,
    var error: String? = null
)
