package com.artemissoftware.domain.usecases.authentication

import BaseUseCaseTest
import com.artemissoftware.domain.models.UserFavoriteImages
import com.artemissoftware.domain.models.profile.User
import com.artemissoftware.domain.repositories.AuthenticationRepository
import com.artemissoftware.domain.repositories.ProfileDataStoreRepository
import com.artemissoftware.domain.usecases.GetUserUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class GetUserUseCaseTest: BaseUseCaseTest() {

    private lateinit var getUserUseCase: GetUserUseCase

    private lateinit var profileDataStoreRepository: ProfileDataStoreRepository
    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        authenticationRepository = mock()
        profileDataStoreRepository = mock()
        getUserUseCase = GetUserUseCase(authenticationRepository = authenticationRepository, profileDataStoreRepository = profileDataStoreRepository)
    }

    @Test
    fun `get user profile and receive the profile with favorites`() = runBlockingTest {

        val userFavoriteImages = UserFavoriteImages(hashMapOf("bb@aa.com" to listOf("1", "2", "3"), "b@b.com" to listOf("xs"), "c@c.com" to listOf("xdd")))
        val userFavoriteImagesFlow = flow { emit(userFavoriteImages) }

        val user = User(email = "bb@aa.com", name = "John the revelator", favorites = emptyList())
        val userFlow = flow { emit(user) }

        val userResult = User(email = "bb@aa.com", name = "John the revelator", favorites = listOf("1", "2", "3"))

        whenever(profileDataStoreRepository.getUserProfile()).thenReturn(
            userFavoriteImagesFlow
        )
        whenever(authenticationRepository.getUser()).thenReturn(
            userFlow
        )

        val emissions = getUserUseCase().toList()

        assert(emissions[0] is User)
        assertEquals(emissions[0], userResult)

        verify(authenticationRepository, times(1)).getUser()
        verify(profileDataStoreRepository, times(1)).getUserProfile()
    }

    @Test
    fun `get user profile and receive the profile with no favorites`() = runBlockingTest {

        val userFavoriteImages = UserFavoriteImages(hashMapOf("bb@aa.com" to listOf("1", "2", "3"), "b@b.com" to listOf("xs"), "c@c.com" to listOf("xdd")))
        val userFavoriteImagesFlow = flow { emit(userFavoriteImages) }

        val user = User(email = "bab@aa.com", name = "John the revelator", favorites = emptyList())
        val userFlow = flow { emit(user) }

        val userResult = User(email = "bab@aa.com", name = "John the revelator")

        whenever(profileDataStoreRepository.getUserProfile()).thenReturn(
            userFavoriteImagesFlow
        )
        whenever(authenticationRepository.getUser()).thenReturn(
            userFlow
        )

        val emissions = getUserUseCase().toList()

        assert(emissions[0] is User)
        assertEquals(emissions[0], userResult)

        verify(authenticationRepository, times(1)).getUser()
        verify(profileDataStoreRepository, times(1)).getUserProfile()
    }
}