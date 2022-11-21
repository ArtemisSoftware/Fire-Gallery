package com.artemissoftware.firegallery.screens.splash

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.setup.SetupAppUseCase
import com.artemissoftware.firegallery.MainActivity
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.graphs.RootDestinations
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
                goToHome()
            }
        }
    }

    private fun loadAppSettings(){

        setupAppUseCase.invoke().onEach { result ->

            when(result){

                is Resource.Success -> {
                    state.dataLoaded = true
                    goToHome()
                }
                is Resource.Error -> {

                    sendUiEvent(UiEvent.ShowDialog(
                            DialogType.Error(
                                title = "Fire Gallery",
                                description = result.message?: "Unknown error",
                                dialogOptions = DialogOptions(
                                    confirmationTextId = R.string.accept,
                                    confirmation = {
                                        onTriggerEvent(SplashEvents.LoadSplash)
                                    }
                                )
                            )
                        )
                    )

                }
                else ->{}
            }
        }.launchIn(viewModelScope)
    }


    private fun goToHome(){
        with(state){
            if(dataLoaded && animationConcluded){
                sendUiEvent(UiEvent.NavigatePopUpTo(currentRoute = RootDestinations.Splash.route, destinationRoute = RootDestinations.Home.route))
            }
        }
    }

}