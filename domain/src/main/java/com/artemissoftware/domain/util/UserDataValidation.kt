package com.artemissoftware.domain.util

object UserDataValidation {

    fun isEmailValid(emailRegex: String, email: String): Boolean {
        return emailRegex.toRegex().matches(email);
    }

    fun validateUsername(username: String, usernameMinLength: Int, usernameMaxLength: Int) : Boolean{

        if (username.isEmpty() || username.isBlank()) {
            return false
        }

        if (username.trim().length < usernameMinLength || username.length > usernameMaxLength) {
            return false
        }

        return true
    }

    fun validatePassword(password: String, passwordMinLength: Int, passwordMaxLength: Int) : Boolean{

        if (password.isEmpty() || password.isBlank()) {
            return false
        }

        if (password.trim().length < passwordMinLength || password.length > passwordMaxLength) {
            return false
        }

        return true
    }


    fun validatePasswordConfirmation(
        password: String,
        passwordConfirm: String, passwordMinLength: Int, passwordMaxLength: Int
    ) : Boolean{

        if (!validatePassword(password, passwordMinLength, passwordMaxLength)) {
            return false
        }

        if(password != passwordConfirm){
            return false
        }

        return true

    }
}