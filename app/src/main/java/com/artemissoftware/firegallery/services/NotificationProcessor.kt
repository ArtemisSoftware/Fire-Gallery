package com.artemissoftware.firegallery.services

import com.artemissoftware.domain.models.LocalNotification
import com.artemissoftware.firegallery.navigation.NavigationArguments.FIRE_GALLERY_URI
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import com.artemissoftware.firegallery.screens.splash.activity.SplashActivity
import java.util.*

object NotificationProcessor {

    private const val CONTENT_TYPE = "contentType"
    private const val CONTENT_VALUE = "contentValue"

    private const val LINK = "link"
    private const val TITLE = "title"
    private const val BODY = "body"


    fun getLocalNotification(data: Map<String, String>): LocalNotification {

        if (data.isNotEmpty()) {

            var deepLink : String? = null

            createDeepLinkFromContent(contentType = data[CONTENT_TYPE], contentValue = data[CONTENT_VALUE])?.let {
                deepLink = "$FIRE_GALLERY_URI/$it"
            }

            data[LINK]?.let {
                deepLink = it
            }

            return LocalNotification(title = data[TITLE], text = data[BODY], link = deepLink, cls = SplashActivity::class.java)
        }

        return LocalNotification()
    }

    private fun createDeepLinkFromContent(contentType: String?, contentValue: String?): String?{

        val destinationRoute = getRoute(contentType)
        return buildDeepLink(destinationRoute, contentValue)
    }

    private fun getRoute(contentType: String?) : String{

        return when {

            contentType.isNullOrBlank() -> DestinationRoutes.HomeGraph.gallery.route
            else -> {contentType.uppercase(Locale.ROOT) }
        }

    }

    private fun buildDeepLink(destinationRoute: String, contentValue: String?) : String?{

        return when(destinationRoute.uppercase(Locale.ROOT)) {

            DestinationRoutes.GalleryGraph.pictureDetail.route->{
                DestinationRoutes.GalleryGraph.pictureDetail.withArgs(contentValue)
            }
            DestinationRoutes.HomeGraph.tinderGallery.route->{
                DestinationRoutes.HomeGraph.tinderGallery.withArgs(contentValue)
            }
            else -> null

        }

    }
}