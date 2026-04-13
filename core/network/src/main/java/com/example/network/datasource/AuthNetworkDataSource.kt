package com.example.network.datasource

import com.example.network.model.AuthResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest

interface AuthNetworkDataSource {
    suspend fun signUp(request: SignupRequest): AuthResponse
    suspend fun login(request: LoginRequest): AuthResponse
}