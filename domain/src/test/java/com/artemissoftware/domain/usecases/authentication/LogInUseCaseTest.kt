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

class LogInUseCaseTest: BaseUseCaseTest() {

    private lateinit var logInUseCase: LogInUseCase

    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        authenticationRepository = mock()
        logInUseCase = LogInUseCase(authenticationRepository)
    }

    @Test
    fun `login user and receive success`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"

        whenever(authenticationRepository.authenticateUser(email = email, password = password)).thenReturn(
            FirebaseResponse(data = true)
        )

        val emissions = logInUseCase(email = email, password = password).toList()

        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)

        result = emissions[1]
        assert(result is Resource.Success)

        verify(authenticationRepository, times(1)).authenticateUser(email = email, password = password)

    }

    @Test
    fun `login user and receive error`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"

        whenever(authenticationRepository.authenticateUser(email = email, password = password)).thenReturn(
            FirebaseResponse(error = FirebaseError(message = "Authentication error"))
        )

        val emissions = logInUseCase(email = email, password = password).toList()

        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)

        result = emissions[1]
        assert(result is Resource.Error)

        verify(authenticationRepository, times(1)).authenticateUser(email = email, password = password)

    }
}