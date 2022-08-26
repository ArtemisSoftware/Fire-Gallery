package com.artemissoftware.data.mappers

import com.artemissoftware.data.local.models.ProfileSettings
import com.artemissoftware.domain.models.profile.Profile

fun ProfileSettings.toProfile() : Profile{
    return Profile(notifications = this.notifications)
}

fun Profile.toProfileSettings() : ProfileSettings{
    return ProfileSettings(notifications = this.notifications)
}