package com.example.domain.model

data class User(
    val id: Int,
    val username: String,
    val fullName: String,
    val uid: String,
    val role: String?
)