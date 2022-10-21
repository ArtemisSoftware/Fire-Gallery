package com.artemissoftware.domain.util

import com.artemissoftware.domain.models.configurations.UserValidationConfig

object UserDataValidation {


    fun isEmailValid(emailRegex: String, email: String): Boolean {
        return emailRegex.toRegex().matches(email);
    }

    fun validatePassword(password: String, userValidationConfigs: UserValidationConfig) : Boolean{

        if (password.isEmpty() || password.isBlank()) {
            return false
        }

        if (password.trim().length < userValidationConfigs.passwordMinLength || password.length > userValidationConfigs.passwordMaxLength) {
            return false
        }

        return true

    }


    fun validatePasswordConfirmation(
        password: String,
        passwordConfirm: String,
        userValidationConfigs: UserValidationConfig
    ) : Boolean{

        if (!validatePassword(password, userValidationConfigs)) {
            return false
        }



        if(password != passwordConfirm){
            return false
        }

        return true

    }
}