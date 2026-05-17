package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.mapper.toRequest
import com.example.domain.model.AuthResult
import com.example.domain.model.CheckUsernameResult
import com.example.domain.model.LoginParams
import com.example.domain.model.SignupParams
import com.example.domain.repository.AuthRepository
import com.example.network.datasource.AuthNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: AuthNetworkDataSource
): AuthRepository {
    override suspend fun signUp(params: SignupParams): Result<AuthResult> {
        return try {
            Result.success(networkDataSource.signUp(params.toRequest()).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(params: LoginParams): Result<AuthResult> {
        return try {
            Result.success(networkDataSource.login(params.toRequest()).toDomain())
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

    override suspend fun checkUsername(username: String): Result<CheckUsernameResult> {
        return try{
            val res = networkDataSource.checkUsername(username).toDomain()
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}