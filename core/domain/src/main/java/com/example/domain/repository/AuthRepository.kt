package com.example.domain.repository

import com.example.domain.model.AuthResult
import com.example.domain.model.CheckUsernameResult
import com.example.domain.model.LoginParams
import com.example.domain.model.SignupParams

interface AuthRepository {
    suspend fun signUp(params: SignupParams): Result<AuthResult>
    suspend fun login(params: LoginParams): Result<AuthResult>
    suspend fun logout(): Result<Unit>
    suspend fun checkUsername(username: String): Result<CheckUsernameResult>
}