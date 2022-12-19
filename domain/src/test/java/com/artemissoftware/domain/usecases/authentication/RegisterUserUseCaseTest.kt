package com.artemissoftware.domain.usecases.authentication

import BaseUseCaseTest
import com.artemissoftware.domain.FirebaseError
import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisterUserUseCaseTest: BaseUseCaseTest() {

    private lateinit var registerUserUseCase: RegisterUserUseCase

    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        authenticationRepository = mock()
        registerUserUseCase = RegisterUserUseCase(authenticationRepository)
    }

    @Test
    fun `register user and receive success`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"
        val username = "userTest"

        whenever(authenticationRepository.registerUser(email = email, password = password, username = username)).thenReturn(
            FirebaseResponse(data = true)
        )

        val emissions = registerUserUseCase(email = email, password = password, username = username).toList()

        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)

        result = emissions[1]
        assert(result is Resource.Success)

        verify(authenticationRepository, times(1)).registerUser(email = email, password = password, username = username)
    }

    @Test
    fun `register user and receive failure`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"
        val username = "userTest"

        whenever(authenticationRepository.registerUser(email = email, password = password, username = username)).thenReturn(
            FirebaseResponse(error = FirebaseError(message = "Registration error"))
        )

        val emissions = registerUserUseCase(email = email, password = password, username = username).toList()

        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)

        result = emissions[1]
        assert(result is Resource.Error)

        verify(authenticationRepository, times(1)).registerUser(email = email, password = password, username = username)
    }
}