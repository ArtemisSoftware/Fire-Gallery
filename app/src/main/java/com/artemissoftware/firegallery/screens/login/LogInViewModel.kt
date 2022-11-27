package com.artemissoftware.firegallery.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.authentication.LogInUseCase
import com.artemissoftware.domain.usecases.authentication.ValidateLoginUseCase
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val loginUseCase: LogInUseCase
) : FGBaseEventViewModel<LogInEvents>() {

    private val _state: MutableStateFlow<LogInState> = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    override fun onTriggerEvent(event: LogInEvents) {
        when(event){

            is LogInEvents.ValidateLogin ->{
                email = event.email ?: email
                password = event.password ?: password
                validateLogIn(email = email, password = password)
            }

            is LogInEvents.LogIn ->{
                logInUser()
            }
            LogInEvents.PopBackStack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun validateLogIn(email: String, password: String) {

        _state.value = _state.value.copy(
            isValidData = validateLoginUseCase.invoke(email = email, password = password).isValid
        )
    }

    private fun logInUser() {
        loginUseCase.invoke(email = email, password = password).onEach { result ->

            when(result) {
                is Resource.Success -> {

                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    sendUiEvent(UiEvent.PopBackStack)
                }
                is Resource.Error -> {

                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    sendUiEvent(UiEvent.ShowDialog(
                            DialogType.Error(
                                title = "Authentication",
                                description = result.message ?: "Unknown error",
                                dialogOptions = DialogOptions(
                                    confirmationTextId = R.string.accept,
                                )
                            )
                        )
                    )

                }
                is Resource.Loading -> {

                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                else ->{}
            }


        }.launchIn(viewModelScope)
    }
}