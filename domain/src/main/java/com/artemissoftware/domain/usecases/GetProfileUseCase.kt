package com.artemissoftware.domain.usecases

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.profile.Profile
import com.artemissoftware.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {


    operator fun invoke(): Flow<Resource<Profile>> = flow {

        emit(Resource.Loading())

        val profile = dataStoreRepository.getProfile().first()

        emit(Resource.Success(data = profile))
    }

}