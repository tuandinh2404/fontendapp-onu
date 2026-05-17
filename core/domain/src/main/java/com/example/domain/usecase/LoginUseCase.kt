package com.example.domain.usecase

import com.example.domain.model.AuthResult
import com.example.domain.model.LoginParams
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(params: LoginParams): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            authRepository.login(params)
        }
    }
}