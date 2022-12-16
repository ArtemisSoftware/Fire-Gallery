package com.artemissoftware.domain.usecases.notifications

import com.artemissoftware.domain.models.LocalNotification
import com.artemissoftware.domain.repositories.LocalNotificationsRepository
import javax.inject.Inject
import javax.print.attribute.standard.Destination

class GenerateLocalNotificationUseCase @Inject constructor(private val localNotificationsRepository: LocalNotificationsRepository) {

    suspend operator fun invoke(localNotification: LocalNotification) = localNotificationsRepository.generateNotification(localNotification = localNotification)


//    suspend operator fun invoke(data: Map<String, String>){
//
//    }



}