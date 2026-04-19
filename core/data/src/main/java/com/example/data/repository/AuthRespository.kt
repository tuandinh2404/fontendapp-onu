package com.example.data.repository

import com.example.network.model.AuthResponse
import com.example.network.model.CheckUsernameResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest

interface AuthRepository {
    suspend fun signUp(request: SignupRequest): Result<AuthResponse>
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun logout(): Result<Unit>
    suspend fun checkUsername(username: String): Result<CheckUsernameResponse>
}