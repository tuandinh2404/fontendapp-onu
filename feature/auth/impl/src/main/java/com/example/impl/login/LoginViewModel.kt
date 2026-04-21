package com.example.impl.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import com.example.datastore.session.SessionManager
import com.example.network.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.LoggingIn
            delay(1000)

            authRepository.login(LoginRequest(username, password))
                .onSuccess { success ->
                    Log.d("LoginViewModel", "Đăng nhập thành công:\n token=${success.token}")
                    sessionManager.saveToken(success.token)
                    _uiState.value = LoginUiState.Success
                }
                .onFailure { error ->
                    Log.e("LoginViewModel", "Đăng nhập thất bại:\n ${error.message}")
                    _uiState.value = LoginUiState.Error(error.message ?: "Lỗi không xác đinh!")
                }
        }

    }

    fun checkUsername(username: String) {
        val usernameRegex = Regex("^[a-zA-Z0-9_]{6,20}$")
        if (!usernameRegex.matches(username)) {
            _uiState.value = LoginUiState.UserNotFound("Tên đăng nhập không hợp lệ")
            return
        }
        viewModelScope.launch {
            _uiState.value = LoginUiState.CheckingUsername
            delay(1000)
            authRepository.checkUsername(username)
                .onSuccess { response  ->
                    if (!response.exists) {
                        _uiState.value = LoginUiState.UserNotFound("Người dùng không tồn tại")
                    } else {
                        _uiState.value = LoginUiState.UserValid
                        Log.d("LoginViewModel", "Tài khoản hợp lệ")
                    }
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Lỗi không xác định")
                }

        }
    }
    fun clearError() {
        if (_uiState.value is LoginUiState.UserNotFound) {
            _uiState.value = LoginUiState.Idle
        }
    }
}