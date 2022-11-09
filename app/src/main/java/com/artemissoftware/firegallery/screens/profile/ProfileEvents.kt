package com.artemissoftware.firegallery.screens.profile

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class ProfileEvents: FGBaseEvents() {
    data class UpdateProfile(val notificationsEnabled: Boolean): ProfileEvents()
    object LogOut: ProfileEvents()
    object GoToLogin : ProfileEvents()
    object GoToRegister : ProfileEvents()
}
