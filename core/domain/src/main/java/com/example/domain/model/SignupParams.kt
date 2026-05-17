package com.example.domain.model

data class SignupParams(
    val username: String,
    val password: String,
    val fullName: String,
    val uid: String
)