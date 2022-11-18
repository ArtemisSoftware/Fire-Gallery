package com.artemissoftware.domain.usecases.notifications

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.AppSettingsDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateFirebaseTokenUseCase @Inject constructor(private val dataStoreRepository: AppSettingsDataStoreRepository) {

    suspend operator fun invoke(firebaseToken : String) = dataStoreRepository.updateFirebaseToken(firebaseToken = firebaseToken)

}