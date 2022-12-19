package com.artemissoftware.firegallery.screens.home

import com.artemissoftware.firegallery.MainEvents
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): FGBaseEventViewModel<MainEvents>() {

    override fun onTriggerEvent(event: MainEvents) {
        when(event){
            is MainEvents.ExecuteDeepLink -> {
                sendUiEvent(UiEvent.DeepLink)
            }
        }
    }
}