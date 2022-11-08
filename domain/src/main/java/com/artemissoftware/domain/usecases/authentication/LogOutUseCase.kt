package com.artemissoftware.domain.usecases.authentication

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    operator fun invoke(): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())
        authenticationRepository.logOut()
        emit(Resource.Success())
    }

}