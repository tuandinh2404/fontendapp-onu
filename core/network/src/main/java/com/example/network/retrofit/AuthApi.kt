package com.example.network.retrofit

import com.example.network.model.AuthResponse
import com.example.network.model.CheckUsernameResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body request: Map<String, String>
    )
    @GET("auth/check-username")
    suspend fun checkUsername(
        @Query("username") username: String
    ): CheckUsernameResponse
}

