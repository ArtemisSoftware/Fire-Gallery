package com.artemissoftware.domain.usecases

import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val profileDataStoreRepository: ProfileDataStoreRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke() = profileDataStoreRepository.getUserProfile().combine(authenticationRepository.getUser()) { userLocalData, user ->

        user?.let { user ->
            userLocalData.data[user.email]?.let { user.favorites = it }
        }
        user
    }

}