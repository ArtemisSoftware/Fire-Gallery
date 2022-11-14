package com.artemissoftware.data.mappers

import com.artemissoftware.data.local.models.AppSettings
import com.artemissoftware.data.local.models.FavoriteImages
import com.artemissoftware.domain.models.UserFavoriteImages
import com.artemissoftware.domain.models.profile.AppConfig
import com.artemissoftware.domain.models.profile.User
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class ProfileMapperKtTest {

    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun setUp() {
        firebaseUser = mock()
        getMockFirebaseUser()
    }

    private fun getMockFirebaseUser() {
        whenever(firebaseUser.displayName).thenReturn("My name")
        whenever(firebaseUser.email).thenReturn("test@test.com")
    }

    @Test
    fun `map AppSettings to AppConfig`() {

        val appSettings = AppSettings(
            notifications = false,
            favorites = listOf("AA", "BB"),
            firebaseToken = "ldfhglskdhbljgdhsngbk"
        )

        val appConfig = AppConfig(
            notifications = false,
            firebaseToken = "ldfhglskdhbljgdhsngbk",
            favorites = listOf("AA", "BB"),
        )

        assertEquals(appConfig, appSettings.toAppConfig())
    }

    @Test
    fun `map AppConfig to AppSettings`() {

        val appSettings = AppSettings(
            notifications = false,
            favorites = listOf("AA", "BB"),
            firebaseToken = "ldfhglskdhbljgdhsngbk"
        )

        val appConfig = AppConfig(
            notifications = false,
            firebaseToken = "ldfhglskdhbljgdhsngbk",
            favorites = listOf("AA", "BB"),
        )

        assertEquals(appSettings, appConfig.toAppSettings())
    }

    @Test
    fun `map FavoriteImages to UserFavoriteImages`() {
        val favoriteImages = FavoriteImages(
            data = hashMapOf("dd" to listOf("AA", "BB"))
        )

        val userFavoriteImages = UserFavoriteImages(
            data = hashMapOf("dd" to listOf("AA", "BB"))
        )

        assertEquals(userFavoriteImages, favoriteImages.toUserFavoriteImages())
    }

    @Test
    fun `map UserFavoriteImages to FavoriteImages`() {

        val favoriteImages = FavoriteImages(
            data = hashMapOf("dd" to listOf("AA", "BB"))
        )

        val userFavoriteImages = UserFavoriteImages(
            data = hashMapOf("dd" to listOf("AA", "BB"))
        )

        assertEquals(favoriteImages, userFavoriteImages.toFavoriteImages())
    }

    @Test
    fun `map FirebaseUser to User`() {

        val user = User(name = "My name", email = "test@test.com")
        assertEquals(user, firebaseUser.toUser())
    }

}