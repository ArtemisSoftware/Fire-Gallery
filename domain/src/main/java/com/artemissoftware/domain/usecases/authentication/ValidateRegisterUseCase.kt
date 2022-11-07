package com.artemissoftware.domain.usecases.authentication

import com.artemissoftware.domain.models.profile.UserValidation
import com.artemissoftware.domain.repositories.RemoteConfigRepository
import com.artemissoftware.domain.util.UserDataValidation
import javax.inject.Inject

class ValidateRegisterUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {

    operator fun invoke(email: String, password: String, passwordConfirm: String, username: String): UserValidation {

        val userValidation = UserValidation()
        var validEmail = false
        var validPassword = false
        var validUsername = false

        if(UserDataValidation.isEmailValid(remoteConfigRepository.getUserValidationConfigs().emailRegex, email)){
            validEmail = true
        }
        else{
            userValidation.emailError = INVALID_EMAIL
        }

        if(UserDataValidation.validatePasswordConfirmation(password, passwordConfirm, remoteConfigRepository.getUserValidationConfigs())){
            validPassword = true
        }
        else{
            userValidation.passwordError = INVALID_PASSWORD
        }

        if(UserDataValidation.validateUsername(username, remoteConfigRepository.getUserValidationConfigs())){
            validUsername = true
        }
        else{
            userValidation.usernameError = INVALID_USERNAME
        }

        userValidation.isValid = validEmail && validPassword && validUsername
        return userValidation
    }

    companion object{

        const val INVALID_EMAIL = "Invalid email"
        const val INVALID_PASSWORD = "Invalid password"
        const val INVALID_USERNAME = "Invalid username"
    }
}