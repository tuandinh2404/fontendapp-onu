package com.example.domain.usecase

import com.example.domain.model.CheckUsernameResult
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String): Result<CheckUsernameResult> {
        val usernameRegex = Regex("^[a-zA-Z0-9_]{6,20}$")
        if(!usernameRegex.matches(username)) {
            return Result.failure(Exception("Tên đăng nhập không hợp lệ"))
        }
        return authRepository.checkUsername(username)
    }
}