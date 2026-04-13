package com.example.network.datasource

import com.example.network.model.AuthResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest
import com.example.network.retrofit.AuthApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAuthNetwork @Inject constructor(
    retrofit: Retrofit
): AuthNetworkDataSource {
    private val api = retrofit.create(AuthApi::class.java)

    override suspend fun signUp(request: SignupRequest): AuthResponse {
        return api.signup(request)
    }

    override suspend fun login(request: LoginRequest): AuthResponse {
        return api.login(request)
    }
}