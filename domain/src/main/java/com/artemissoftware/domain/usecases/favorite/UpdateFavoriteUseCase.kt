package com.artemissoftware.domain.usecases.favorite

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.AppSettingsDataStoreRepository
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val profileDataStoreRepository: ProfileDataStoreRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(pictureId : String, isFavorite: Boolean) {

        val user = authenticationRepository.getUser().first()
        user?.let {
            profileDataStoreRepository.updateFavorite(pictureId = pictureId, isFavorite = isFavorite, email = it.email)
        }

    }
}