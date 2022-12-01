package com.artemissoftware.domain.usecases.favorite

import BaseUseCaseTest
import com.artemissoftware.domain.models.profile.User
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateFavoriteUseCaseTest: BaseUseCaseTest() {

    private lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var profileDataStoreRepository: ProfileDataStoreRepository

    @Before
    fun setUp() {
        authenticationRepository = mock()
        profileDataStoreRepository = mock()
        updateFavoriteUseCase = UpdateFavoriteUseCase(authenticationRepository = authenticationRepository, profileDataStoreRepository = profileDataStoreRepository)
    }

    @Test
    fun `set picture as favorite for existing user`() = runBlockingTest {

        val pictureId = "AABB"
        val isFavorite = true

        val user = User(email ="test@test.com", name = "testuser", favorites = emptyList())
        val userFlow = flow { emit(user) }

        whenever(authenticationRepository.getUser()).thenReturn(
            userFlow
        )

        updateFavoriteUseCase(pictureId = pictureId, isFavorite = isFavorite)


        verify(authenticationRepository, times(1)).getUser()
        verify(profileDataStoreRepository, times(1)).updateFavorite(pictureId = pictureId, isFavorite = isFavorite, email = user.email)
    }

    @Test
    fun `remove favorite for existing user`() = runBlockingTest {

        val pictureId = "AABB"
        val isFavorite = false

        val user = User(email ="test@test.com", name = "testuser", favorites = emptyList())
        val userFlow = flow { emit(user) }

        whenever(authenticationRepository.getUser()).thenReturn(
            userFlow
        )

        updateFavoriteUseCase(pictureId = pictureId, isFavorite = isFavorite)


        verify(authenticationRepository, times(1)).getUser()
        verify(profileDataStoreRepository, times(1)).updateFavorite(pictureId = pictureId, isFavorite = isFavorite, email = user.email)

    }


    @Test
    fun `set picture as favorite for non existing user does nato update`() = runBlockingTest {

        val pictureId = "AABB"
        val isFavorite = false

        val user = User(email ="test@test.com", name = "testuser", favorites = emptyList())
        val userFlow = flow { emit(null) }

        whenever(authenticationRepository.getUser()).thenReturn(
            userFlow
        )

        updateFavoriteUseCase(pictureId = pictureId, isFavorite = isFavorite)


        verify(authenticationRepository, times(1)).getUser()
        verify(profileDataStoreRepository, times(0)).updateFavorite(pictureId = pictureId, isFavorite = isFavorite, email = user.email)

    }
}
