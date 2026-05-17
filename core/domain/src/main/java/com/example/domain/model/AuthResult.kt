package com.example.domain.model

data class AuthResult(
    val user: User,
    val token: String,
    val refreshToken: String  // bỏ @SerializedName
)