package com.artemissoftware.firegallery.screens.splash.activity

import androidx.lifecycle.viewModelScope
import com.artemissoftware.firegallery.screens.splash.SplashEvents
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.FGScaffoldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(): FGBaseEventViewModel<SplashEvents>() {

    val scaffoldState by lazy { FGScaffoldState(viewModelScope) }
}