package com.artemissoftware.domain.usecases.validation

import com.artemissoftware.domain.repositories.RemoteConfigRepository
import javax.inject.Inject

class GetValidationRulesUseCase @Inject constructor(private val remoteConfigRepository: RemoteConfigRepository) {

    operator fun invoke() = remoteConfigRepository.getUserValidationConfigs()

}