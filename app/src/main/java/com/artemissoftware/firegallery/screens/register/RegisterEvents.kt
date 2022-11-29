package com.artemissoftware.firegallery.screens.register

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class RegisterEvents: FGBaseEvents() {
    data class ValidateRegister(val email: String? = null, val password: String? = null, val passwordConfirm: String? = null, val username: String? = null): RegisterEvents()
    object Register: RegisterEvents()
    object GetValidationRules : RegisterEvents()
    object PopBackStack : RegisterEvents()
}
