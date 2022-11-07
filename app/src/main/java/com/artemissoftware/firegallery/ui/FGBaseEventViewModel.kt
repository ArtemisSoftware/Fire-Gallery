package com.artemissoftware.firegallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class FGBaseEventViewModel<E: FGBaseEvents> : ViewModel() {


    protected val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val __uiEvent =  Channel<UiEvent>()
    val uiEventLolo = __uiEvent.receiveAsFlow()



    open fun onTriggerEvent(event: E) {}

    protected fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            __uiEvent.send(event)
        }
    }
}