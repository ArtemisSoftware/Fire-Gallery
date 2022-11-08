package com.artemissoftware.firegallery.screens.login

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class LogInEvents: FGBaseEvents() {

    data class ValidateLogin(val email: String? = null, val password: String? = null): LogInEvents()
    object LogIn : LogInEvents()
}