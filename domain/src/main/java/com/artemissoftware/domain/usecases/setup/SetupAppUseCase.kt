package com.artemissoftware.domain.usecases.setup

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetupAppUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {

    operator fun invoke(): Flow<Resource<Any>> = flow {

        val result = remoteConfigRepository.fetchValues()

        result.data?.let {
            emit(Resource.Success())
        } ?: kotlin.run {
            emit(Resource.Error(message = result.error.message))

        }
    }

}