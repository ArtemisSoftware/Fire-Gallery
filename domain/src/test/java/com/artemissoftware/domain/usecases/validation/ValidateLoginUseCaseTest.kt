package com.artemissoftware.domain.usecases.validation

import BaseUseCaseTest
import com.artemissoftware.domain.models.configurations.UserValidationConfig
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.usecases.validation.ValidateLoginUseCase.Companion.INVALID_EMAIL
import com.artemissoftware.domain.usecases.validation.ValidateLoginUseCase.Companion.INVALID_PASSWORD
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ValidateLoginUseCaseTest: BaseUseCaseTest() {

    private lateinit var validateLoginUseCase: ValidateLoginUseCase

    private lateinit var remoteConfigRepository: RemoteConfigRepository

    @Before
    fun setUp() {
        remoteConfigRepository = mock()
        validateLoginUseCase = ValidateLoginUseCase(remoteConfigRepository)
    }

    @Test
    fun `valid email and password return success`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateLoginUseCase(email = email, password = password)

        verify(remoteConfigRepository, times(2)).getUserValidationConfigs()
        assertEquals(true, userValidation.isValid)
        assertEquals(userValidation.emailError, "")
        assertEquals(userValidation.passwordError, "")

    }

    @Test
    fun `invalid email and valid password return failure`() = runBlockingTest {

        val email = "test"
        val password = "123456"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateLoginUseCase(email = email, password = password)

        verify(remoteConfigRepository, times(2)).getUserValidationConfigs()

        assertEquals(userValidation.isValid, false)
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, "")
    }

    @Test
    fun `valid email and invalid password return failure`() = runBlockingTest {

        val email = "test@test.com"
        val password = "1"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateLoginUseCase(email = email, password = password)


        verify(remoteConfigRepository, times(2)).getUserValidationConfigs()

        assertEquals(userValidation.isValid, false)
        assertEquals(userValidation.emailError, "")
        assertEquals(userValidation.passwordError, INVALID_PASSWORD)

    }


    @Test
    fun `invalid email and password return failure`() = runBlockingTest {

        val email = "test@test"
        val password = "1"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateLoginUseCase(email = email, password = password)


        verify(remoteConfigRepository, times(2)).getUserValidationConfigs()

        assertEquals(userValidation.isValid, false)
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, INVALID_PASSWORD)

    }

    private fun getUserValidationConfig() = UserValidationConfig(
        emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})",
        usernameRegex = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$",
        passwordMaxLength = 8,
        passwordMinLength = 3,
        usernameMaxLength = 12,
        usernameMinLength = 8,
    )
}