package com.artemissoftware.domain.usecases.authentication

import BaseUseCaseTest
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class LogOutUseCaseTest: BaseUseCaseTest() {

    private lateinit var logOutUseCase: LogOutUseCase

    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        authenticationRepository = mock()
        logOutUseCase = LogOutUseCase(authenticationRepository)
    }

    @Test
    fun `logout user`() = runBlockingTest {

        val emissions = logOutUseCase().toList()
        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Success)

        verify(authenticationRepository, times(1)).logOut()

    }
}