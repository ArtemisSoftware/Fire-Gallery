package com.artemissoftware.firegallery.services

import com.artemissoftware.domain.models.LocalNotification
import com.artemissoftware.domain.usecases.notifications.UpdateFirebaseTokenUseCase
import com.artemissoftware.domain.usecases.notifications.GenerateLocalNotificationUseCase
import com.artemissoftware.firegallery.MainActivity
import com.artemissoftware.firegallery.screens.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        val localNotification = getLocalNotification(remoteMessage.data)
        sendNotification(localNotification)
    }


    private fun getLocalNotification(data: MutableMap<String, String>): LocalNotification{

        if (data.isNotEmpty()) {
            with(data){
                return LocalNotification(title = get(TITLE), text = get(BODY), link = get(LINK), cls = SplashActivity::class.java)
            }
        }

        return LocalNotification()
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

    companion object{
        private const val LINK = "link"
        private const val TITLE = "title"
        private const val BODY = "body"
    }
}