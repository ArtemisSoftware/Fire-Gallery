package com.artemissoftware.firegallery.screens.splash.activity

import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.screens.splash.SplashEvents
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import javax.inject.Inject

class SplashActivityViewModel @Inject constructor(): FGBaseEventViewModel<SplashEvents>() {

    val scaffoldState by lazy { FGScaffoldState(viewModelScope) }
}