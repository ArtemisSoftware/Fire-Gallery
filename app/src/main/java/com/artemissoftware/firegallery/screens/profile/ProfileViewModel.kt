package com.artemissoftware.firegallery.screens.profile

import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.profile.AppConfig
import com.artemissoftware.domain.usecases.authentication.LogOutUseCase
import com.artemissoftware.domain.usecases.profile.GetProfileUseCase
import com.artemissoftware.domain.usecases.profile.UpdateProfileUseCase
import com.artemissoftware.firegallery.navigation.graphs.ProfileDestinations
import com.artemissoftware.firegallery.screens.profile.ProfileEvents.UpdateProfile
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val logOutUseCase: LogOutUseCase
): FGBaseEventViewModel<ProfileEvents>() {


    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state


    init {
        getGetProfile()
    }

    override fun onTriggerEvent(event: ProfileEvents) {
        when(event){

            is UpdateProfile->{
                updateProfile(notificationsEnabled = event.notificationsEnabled)
            }
            is ProfileEvents.LogOut->{
                logOut()
            }
            ProfileEvents.GoToLogin -> {
                sendUiEvent(UiEvent.Navigate(ProfileDestinations.LogInUser.route))
            }
            ProfileEvents.GoToRegister -> {
                sendUiEvent(UiEvent.Navigate(ProfileDestinations.RegisterUser.route))
            }
        }
    }

    private fun getGetProfile(){

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true
            )

            getProfileUseCase.invoke().collectLatest { result ->

                _state.value = _state.value.copy(
                    profile = result,
                    isLoading = false
                )
            }
        }
    }

    private fun updateProfile(notificationsEnabled: Boolean){

        val appConfig = AppConfig(notifications = notificationsEnabled)

        viewModelScope.launch {
            updateProfileUseCase.invoke(appConfig)
        }

    }

    private fun logOut(){

        logOutUseCase.invoke().onEach { result ->

            _state.value = _state.value.copy(
                isLoading = when(result) {
                    is Resource.Loading -> {
                        true
                    }
                    else ->{ false }
                }
            )

        }.launchIn(viewModelScope)
    }
}

