package com.artemissoftware.domain.usecases.validation

import com.artemissoftware.domain.models.profile.UserValidation
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.util.UserDataValidation
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {

    operator fun invoke(email: String, password: String): UserValidation  {

        val userValidation = UserValidation()
        var validEmail = false
        var validPassword = false

        with(remoteConfigRepository.getUserValidationConfigs()){

            if(UserDataValidation.isEmailValid(emailRegex, email)){
                validEmail = true
            }
            else{
                userValidation.emailError = INVALID_EMAIL
            }

            if(UserDataValidation.validatePassword(password, passwordMinLength, passwordMaxLength)){
                validPassword = true
            }
            else{
                userValidation.passwordError = INVALID_PASSWORD
            }
        }
        userValidation.isValid = validEmail && validPassword
        return userValidation
    }

    companion object{

        const val INVALID_EMAIL = "Invalid email"
        const val INVALID_PASSWORD = "Invalid password"
    }
}