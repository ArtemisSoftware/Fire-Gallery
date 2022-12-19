package com.artemissoftware.data.repositories

import com.artemissoftware.data.errors.FireGalleryException
import com.artemissoftware.data.firebase.HandleFirebase
import com.artemissoftware.data.firebase.entities.PictureFso
import com.artemissoftware.data.firebase.source.RemoteConfigSource
import com.artemissoftware.data.mappers.toFirebaseError
import com.artemissoftware.data.mappers.toSeasonConfig
import com.artemissoftware.data.mappers.toSeasonDetailConfig
import com.artemissoftware.data.mappers.toUserValidationConfig
import com.artemissoftware.data.util.SeasonUtil
import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.models.configurations.SeasonDetailConfig
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.util.SeasonType
import com.google.firebase.auth.FirebaseUser
import java.util.*

class RemoteConfigRepositoryImpl(
    private val remoteConfigSource: RemoteConfigSource
) : RemoteConfigRepository {

    override suspend fun fetchValues(): FirebaseResponse<Boolean> {

        return try {

            val response = HandleFirebase.safeApiCall<Boolean, Boolean>{  remoteConfigSource.fetchValues() }

            return FirebaseResponse(data = response)

        } catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }
    }

    override fun getSeasonConfigs() = remoteConfigSource.seasonConfig.toSeasonConfig()

    override fun getSeasonConfig(season: SeasonType?): SeasonDetailConfig {

        if(season == null){

            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH) + 1
            val seasonType = SeasonUtil.findSeason(month = month)
            return remoteConfigSource.seasonConfig.toSeasonDetailConfig(seasonType)
        }
        else{
            return remoteConfigSource.seasonConfig.toSeasonDetailConfig(season)
        }
    }

    override fun getUserValidationConfigs() = remoteConfigSource.userValidationConfig.toUserValidationConfig()


}