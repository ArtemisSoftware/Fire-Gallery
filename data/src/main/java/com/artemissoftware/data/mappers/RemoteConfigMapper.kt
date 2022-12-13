package com.artemissoftware.data.mappers

import com.artemissoftware.data.firebase.remoteconfig.RemoteConfigContants
import com.artemissoftware.data.firebase.remoteconfig.models.ChipColorRco
import com.artemissoftware.data.firebase.remoteconfig.models.SeasonDetailRco
import com.artemissoftware.data.firebase.remoteconfig.models.SeasonRco
import com.artemissoftware.data.firebase.remoteconfig.models.UserValidationRco
import com.artemissoftware.domain.models.configurations.ChipColorConfig
import com.artemissoftware.domain.models.configurations.SeasonConfig
import com.artemissoftware.domain.models.configurations.SeasonDetailConfig
import com.artemissoftware.domain.models.configurations.UserValidationConfig
import com.artemissoftware.domain.util.SeasonType
import com.google.common.reflect.TypeToken
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson

fun FirebaseRemoteConfig.toSeasonConfig() : SeasonRco{
    return Gson().fromJson(this.getString(RemoteConfigContants.SEASON_CONFIG), object: TypeToken<SeasonRco>(){}.type)
}

fun FirebaseRemoteConfig.toUserValidationConfig() : UserValidationRco{
    return Gson().fromJson(this.getString(RemoteConfigContants.VALIDATION_CONFIG), object: TypeToken<UserValidationRco>(){}.type)
}

fun SeasonDetailRco.toSeasonDetailConfig(): SeasonDetailConfig {
    return SeasonDetailConfig(this.chipColor.toChipColorConfig(), icon = this.icon)
}

fun SeasonRco.toSeasonConfig(): SeasonConfig {
    return SeasonConfig(chipColor = this.chipColor.toChipColorConfig())
}

fun SeasonRco.toSeasonDetailConfig(seasonType: SeasonType): SeasonDetailConfig {

    val season = when(seasonType){
        SeasonType.SPRING -> spring
        SeasonType.SUMMER -> summer
        SeasonType.AUTUMN -> autumn
        SeasonType.WINTER -> winter
    }

    return season.toSeasonDetailConfig()
}

fun ChipColorRco.toChipColorConfig(): ChipColorConfig {
    return ChipColorConfig(this.start, this.end, this.icon)
}



fun UserValidationRco.toUserValidationConfig(): UserValidationConfig {
    return UserValidationConfig(
        emailRegex = this.emailRegex,
        passwordMaxLength = this.passwordMaxLength,
        passwordMinLength = this.passwordMinLength,
        usernameRegex = this.usernameRegex,
        usernameMaxLength = this.usernameMaxLength,
        usernameMinLength = this.usernameMinLength
    )
}