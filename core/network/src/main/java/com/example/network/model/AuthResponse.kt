package com.example.network.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val user: User,
    val token: String,
    val refreshToken: String
)

data class User(
    val id: Int,
    val username: String,

    @SerializedName("full_name")
    val fullName: String,
    val uid: String,
    val role: String?
)