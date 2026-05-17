package com.example.impl.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SignupParams
import com.example.domain.repository.AuthRepository
import com.example.domain.usecase.CheckUsernameUseCase
import com.example.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterFormState(
    val username: String = "",
    val password: String = "",
    val fullName: String = "",
    val uid: String = "",
    val step: SignupStep = SignupStep.USERNAME,
    val isUsernameValid: Boolean = false
)

enum class SignupStep { USERNAME, PASSWORD, FULLNAME, UID }

fun SignupStep.getStepNumber(): Int {
    return when (this) {
        SignupStep.USERNAME -> 1
        SignupStep.PASSWORD -> 2
        SignupStep.FULLNAME -> 3
        SignupStep.UID -> 4
    }
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkUsernameUseCase: CheckUsernameUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _formState = MutableStateFlow(RegisterFormState())
    val formState: StateFlow<RegisterFormState> = _formState

    private val _currentStep = MutableStateFlow(2)
    val currentStep : StateFlow<Int> = _currentStep

    fun signUp(password: String, fullName: String, uid: String) {
        val username = _formState.value.username
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            signUpUseCase(SignupParams(username, password, fullName, uid))
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
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            checkUsernameUseCase(username)
                .onSuccess { response  ->
                    if (response.exists) {
                        _uiState.value = RegisterUiState.Error("Người dùng đã tồn tại")
                    } else {
                        _formState.update {
                            it.copy(
                                username = username,
                                isUsernameValid = true,
                                step = SignupStep.PASSWORD
                            )
                        }
                        _uiState.value = RegisterUiState.Idle
                    }
                }
                .onFailure { error ->
                    _uiState.value = RegisterUiState.Error(error.message ?: "Lỗi không xác định")
                }

        }
    }

    fun nextStep(step: SignupStep) {
        _formState.update { it.copy(step = step)}
    }
    fun clearError() {
        if (_uiState.value is RegisterUiState.Error) {
            _uiState.value = RegisterUiState.Idle
        }
    }
    fun goBack() {
        val previousStep = when (_formState.value.step) {
            SignupStep.USERNAME -> null
            SignupStep.PASSWORD -> SignupStep.USERNAME
            SignupStep.FULLNAME -> SignupStep.PASSWORD
            SignupStep.UID -> SignupStep.FULLNAME
        }
        if (previousStep != null) {
            _formState.update { it.copy(step = previousStep) }
        }
    }
}