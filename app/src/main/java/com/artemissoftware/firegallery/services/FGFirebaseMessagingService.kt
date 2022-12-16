package com.artemissoftware.firegallery.services

import com.artemissoftware.domain.models.LocalNotification
import com.artemissoftware.domain.usecases.notifications.UpdateFirebaseTokenUseCase
import com.artemissoftware.domain.usecases.notifications.GenerateLocalNotificationUseCase
import com.artemissoftware.firegallery.screens.splash.activity.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FGFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    internal lateinit var updateFirebaseTokenUseCase: UpdateFirebaseTokenUseCase

    @Inject
    internal lateinit var generateLocalNotificationUseCase: GenerateLocalNotificationUseCase

    private val job = SupervisorJob()

    override fun onNewToken(token: String) {
        saveToken(token = token)
        super.onNewToken(token)
    }


    private fun saveToken(token: String){

        CoroutineScope(job).launch {
            updateFirebaseTokenUseCase.invoke(token)
        }
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val localNotification = NotificationProcessor.getLocalNotification(remoteMessage.data)
        sendNotification(localNotification)
    }


    private fun sendNotification(localNotification: LocalNotification) {

        CoroutineScope(job).launch {
            generateLocalNotificationUseCase.invoke(localNotification)
        }
    }



    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}