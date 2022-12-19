package com.artemissoftware.firegallery.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.validation.GetValidationRulesUseCase
import com.artemissoftware.domain.usecases.authentication.RegisterUserUseCase
import com.artemissoftware.domain.usecases.validation.ValidateRegisterUseCase
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
class RegisterViewModel @Inject constructor(
    private val validateRegisterUseCase: ValidateRegisterUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getValidationRulesUseCase: GetValidationRulesUseCase
): FGBaseEventViewModel<RegisterEvents>() {

    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    var email by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    init {
        getValidationRules()
    }

    override fun onTriggerEvent(event: RegisterEvents) {
        when(event){

            is RegisterEvents.ValidateRegister ->{
                email = event.email.orEmpty()
                username = event.username.orEmpty()
                password = event.password.orEmpty()
                confirmPassword = event.passwordConfirm.orEmpty()
                validateRegister(email = email, password = password, passwordConfirm = confirmPassword, username = username)
            }

            is RegisterEvents.Register ->{
                registerUser()
            }
            RegisterEvents.PopBackStack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun getValidationRules(){
        _state.value = _state.value.copy(
            validationRules = getValidationRulesUseCase.invoke()
        )
    }


    private fun validateRegister(email: String, password: String, passwordConfirm: String, username: String){

        _state.value = _state.value.copy(
            isValidData = validateRegisterUseCase.invoke(email = email, password = password, passwordConfirm = passwordConfirm, username = username).isValid
        )
    }


    private fun registerUser(){
        registerUserUseCase.invoke(email = email, password = password, username = username).onEach { result ->

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

                    sendUiEvent(
                        UiEvent.ShowDialog(
                            DialogType.Error(
                                title = "Register",
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