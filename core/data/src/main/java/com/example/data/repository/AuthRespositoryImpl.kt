package com.example.data.repository

import com.example.network.datasource.AuthNetworkDataSource
import com.example.network.model.AuthResponse
import com.example.network.model.CheckUsernameResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: AuthNetworkDataSource
): AuthRepository {
    override suspend fun signUp(request: SignupRequest): Result<AuthResponse> {
        return try {
            Result.success(networkDataSource.signUp(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            Result.success(networkDataSource.login(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkUsername(username: String): Result<CheckUsernameResponse> {
        return try{
            val res = networkDataSource.checkUsername(username)
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}