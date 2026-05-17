package com.example.network.model

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    val user: UserResponse,
    val token: String,
    val refreshToken: String
)