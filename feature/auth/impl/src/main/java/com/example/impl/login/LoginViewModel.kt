package com.example.impl.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.session.SessionManager
import com.example.domain.model.LoginParams
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.CheckUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.LoggingIn

            loginUseCase(LoginParams(username, password))
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
        viewModelScope.launch {
            _uiState.value = LoginUiState.CheckingUsername
            checkUsernameUseCase(username)
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