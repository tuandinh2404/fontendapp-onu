package com.example.impl.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object UserValid : LoginUiState()
    object InvalidPassword : LoginUiState()
    object CheckingUsername : LoginUiState()
    object LoggingIn : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String): LoginUiState()
    data class UserNotFound(val message: String): LoginUiState()
}