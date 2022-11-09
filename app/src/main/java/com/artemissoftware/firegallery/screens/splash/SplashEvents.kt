package com.artemissoftware.firegallery.screens.splash

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class SplashEvents: FGBaseEvents() {
    object LoadSplash: SplashEvents()
    object AnimationConcluded : SplashEvents()
}

