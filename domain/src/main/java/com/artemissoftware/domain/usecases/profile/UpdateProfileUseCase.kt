package com.artemissoftware.domain.usecases.profile

import com.artemissoftware.domain.models.profile.AppConfig
import com.artemissoftware.domain.repositories.AppSettingsDataStoreRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val dataStoreRepository: AppSettingsDataStoreRepository) {

    suspend operator fun invoke(appConfig : AppConfig) {
        dataStoreRepository.saveAppSettings(appConfig)
    }

}