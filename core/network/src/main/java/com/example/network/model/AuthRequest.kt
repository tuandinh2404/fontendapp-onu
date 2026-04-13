package com.example.network.model

import com.google.gson.annotations.SerializedName

data class SignupRequest (
    val username: String,
    val password: String,

    @SerializedName("full_name")
    val fullName: String,
    val uid: String
)
data class LoginRequest (
    val username: String,
    val password: String
)