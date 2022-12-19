package com.artemissoftware.domain.usecases.validation

import BaseUseCaseTest
import com.artemissoftware.domain.models.configurations.UserValidationConfig
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.usecases.validation.ValidateLoginUseCase.Companion.INVALID_EMAIL
import com.artemissoftware.domain.usecases.validation.ValidateLoginUseCase.Companion.INVALID_PASSWORD
import com.artemissoftware.domain.usecases.validation.ValidateRegisterUseCase.Companion.INVALID_USERNAME
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ValidateRegisterUseCaseTest : BaseUseCaseTest() {

    private lateinit var validateRegisterUseCase: ValidateRegisterUseCase

    private lateinit var remoteConfigRepository: RemoteConfigRepository

    @Before
    fun setUp() {
        remoteConfigRepository = mock()
        validateRegisterUseCase = ValidateRegisterUseCase(remoteConfigRepository)
    }


    @Test
    fun `valid email password passwordConfirm username return success`() = runBlockingTest {

        val email = "test@test.com"
        val password = "123456"
        val passwordConfirm = "123456"
        val username = "userTester"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(true, userValidation.isValid)
        assertEquals(userValidation.usernameError, "")
        assertEquals(userValidation.emailError, "")
        assertEquals(userValidation.passwordError, "")

    }

    @Test
    fun `invalid email and valid password passwordConfirm username return failure`() = runBlockingTest {

        val email = "test"
        val password = "123456"
        val passwordConfirm = "123456"
        val username = "userTester"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(false, userValidation.isValid)
        assertEquals(userValidation.usernameError, "")
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, "")
    }

    @Test
    fun `invalid email password and valid passwordConfirm username return failure`() = runBlockingTest {

        val email = "test"
        val password = "1"
        val passwordConfirm = "123456"
        val username = "userTester"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(false, userValidation.isValid)
        assertEquals(userValidation.usernameError, "")
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, INVALID_PASSWORD)
    }


    @Test
    fun `invalid email password passwordConfirmand valid username return failure`() = runBlockingTest {

        val email = "test"
        val password = "1"
        val passwordConfirm = "1"
        val username = "userTester"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(false, userValidation.isValid)
        assertEquals(userValidation.usernameError, "")
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, INVALID_PASSWORD)
    }

    @Test
    fun `invalid email password passwordConfirmand username return failure`() = runBlockingTest {

        val email = "test"
        val password = "1"
        val passwordConfirm = "1"
        val username = "us"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(false, userValidation.isValid)
        assertEquals(userValidation.usernameError, INVALID_USERNAME)
        assertEquals(userValidation.emailError, INVALID_EMAIL)
        assertEquals(userValidation.passwordError, INVALID_PASSWORD)
    }


    @Test
    fun `different password and passwordConfirm return success`() = runBlockingTest {

        val email = "test@test.com"
        val password = "12345"
        val passwordConfirm = "123456"
        val username = "userTester"

        whenever(remoteConfigRepository.getUserValidationConfigs()).thenReturn(
            getUserValidationConfig()
        )

        val userValidation = validateRegisterUseCase(email = email, password = password, passwordConfirm = passwordConfirm, username = username)

        verify(remoteConfigRepository, times(1)).getUserValidationConfigs()
        assertEquals(false, userValidation.isValid)
        assertEquals(userValidation.usernameError, "")
        assertEquals(userValidation.emailError, "")
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