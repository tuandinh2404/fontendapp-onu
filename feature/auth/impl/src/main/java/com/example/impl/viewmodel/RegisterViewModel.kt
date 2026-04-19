package com.example.impl.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import com.example.network.model.SignupRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    object UserValid : RegisterUiState()
    object Registing: RegisterUiState()
    object CheckingUsername : RegisterUiState()
    data class Error(val message: String): RegisterUiState()
    data class UserNotFound(val message: String): RegisterUiState()


}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun signUp(username: String, password: String, fullName: String, uid: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Registing
            delay(1000)

            authRepository.signUp(
                SignupRequest(
                    username,
                    password,
                    fullName,
                    uid
                )
            )
                .onSuccess { success ->
                    Log.d("RegisterViewModel", "Đăng ký thành công:\n token=${success.token}")
                    _uiState.value = RegisterUiState.Success
                }
                .onFailure { error ->
                    Log.e("RegisterViewModel", "Đăng ký thất bại:\n ${error.message}")
                    _uiState.value = RegisterUiState.Error(error.message ?: "Lỗi không xác định")
                }
        }
    }

    fun checkUsername(username: String) {
        val usernameRegex = Regex("^[a-zA-Z0-9_]{6,20}$")
        if (!usernameRegex.matches(username)) {
            _uiState.value = RegisterUiState.UserNotFound("Tên đăng kí không hợp lệ")
            return
        }
        viewModelScope.launch {
            _uiState.value = RegisterUiState.CheckingUsername
            delay(1000)
            authRepository.checkUsername(username)
                .onSuccess { response  ->
                    if (response.exists) {
                        _uiState.value = RegisterUiState.UserNotFound("Người dùng đã tồn tại")
                    } else {
                        _uiState.value = RegisterUiState.UserValid
                        Log.d("RegisterViewModel", "Tài khoản hợp lệ")
                    }
                }
                .onFailure { error ->
                    _uiState.value = RegisterUiState.Error(error.message ?: "Lỗi không xác định")
                }

        }
    }
    fun clearError() {
        if (_uiState.value is RegisterUiState.UserNotFound) {
            _uiState.value = RegisterUiState.Idle
        }
    }
}