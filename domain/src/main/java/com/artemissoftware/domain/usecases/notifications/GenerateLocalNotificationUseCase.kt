package com.artemissoftware.domain.usecases.notifications

import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.LocalNotification
import com.artemissoftware.domain.repositories.LocalNotificationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateLocalNotificationUseCase @Inject constructor(private val localNotificationsRepository: LocalNotificationsRepository) {

    suspend operator fun invoke(localNotification: LocalNotification) = localNotificationsRepository.generateNotification(localNotification = localNotification)
}