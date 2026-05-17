package com.example.domain.usecase

import com.example.domain.model.AuthResult
import com.example.domain.model.SignupParams
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(params: SignupParams): Result<AuthResult> {
        return authRepository.signUp(params)
    }
}