package com.artemissoftware.firegallery.screens.splash

import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.FirebaseError
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.setup.SetupAppUseCase
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val setupAppUseCase: SetupAppUseCase
): FGBaseEventViewModel<SplashEvents>() {

    private val _state: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    init {
        loadAppSettings()
    }

    override fun onTriggerEvent(event: SplashEvents) {
        when(event){

            is SplashEvents.LoadSplash ->{
                loadAppSettings()
            }
        }
    }

    private fun loadAppSettings(){

        setupAppUseCase.invoke().onEach { result ->

            when(result){

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        dataLoaded = true
                    )
                }
                is Resource.Error -> {
                    showDialog(result.message ?: "", (result.data as FirebaseError).message)
                }
                else ->{}
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun showDialog(message: String, messageAppend: String){

        when(message){

            SetupAppUseCase.ERROR_READING_REMOTE_CONFIG ->{

                _uiEvent.emit(
                    UiEvent.ShowErrorDialog(
                        title = "Fire Gallery",
                        message = messageAppend
                    )
                )
            }
        }
    }

}