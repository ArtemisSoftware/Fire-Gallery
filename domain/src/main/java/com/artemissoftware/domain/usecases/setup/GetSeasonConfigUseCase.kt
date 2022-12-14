package com.artemissoftware.domain.usecases.setup

import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.util.SeasonType
import javax.inject.Inject

class GetSeasonConfigUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
){
    operator fun invoke(season: SeasonType?) = remoteConfigRepository.getSeasonConfig(season)
}