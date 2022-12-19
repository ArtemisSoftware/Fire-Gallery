package com.artemissoftware.firegallery.screens.splash

import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.setup.SetupAppUseCase
import com.artemissoftware.firegallery.MainActivity
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val setupAppUseCase: SetupAppUseCase
): FGBaseEventViewModel<SplashEvents>() {

    private var state = SplashState()

    init {
        loadAppSettings()
    }

    override fun onTriggerEvent(event: SplashEvents) {
        when(event){

            is SplashEvents.LoadSplash ->{
                loadAppSettings()
            }
            SplashEvents.AnimationConcluded -> {
                state.animationConcluded = true
                endSplash()
            }
        }
    }

    private fun loadAppSettings(){

        setupAppUseCase.invoke().onEach { result ->

            when(result){

                is Resource.Success -> {
                    state.dataLoaded = true
                }
                is Resource.Error -> {
                    state.error = result.message?: "Unknown error"
                }
                else ->{}
            }

            endSplash()
        }.launchIn(viewModelScope)
    }


    private fun endSplash() {
        with(state){
            if(dataLoaded && animationConcluded){
                sendUiEvent(UiEvent.FinishAndStartActivity(MainActivity::class.java))
            }

            error?.let { message ->

                if(animationConcluded){
                    sendUiEvent(UiEvent.ShowDialog(
                        DialogType.Error(
                            title = "Fire Gallery",
                            description = message,
                            dialogOptions = DialogOptions(
                                confirmationTextId = R.string.accept,
                                confirmation = {
                                    error = null
                                    onTriggerEvent(SplashEvents.LoadSplash)
                                }
                            )
                        )
                    )
                    )
                }
            }


        }
    }

}