package com.example.data.mapper

import com.example.domain.model.AuthResult
import com.example.domain.model.CheckUsernameResult
import com.example.domain.model.LoginParams
import com.example.domain.model.SignupParams
import com.example.domain.model.User
import com.example.network.model.AuthResponse
import com.example.network.model.CheckUsernameResponse
import com.example.network.model.LoginRequest
import com.example.network.model.SignupRequest
import com.example.network.model.UserResponse

fun LoginParams.toRequest() = LoginRequest(
    username = username,
    password = password
)

fun SignupParams.toRequest() = SignupRequest(
    username = username,
    password = password,
    fullName = fullName,
    uid = uid
)

fun UserResponse.toDomain() = User(
    id = id,
    username = username,
    fullName = fullName,
    uid = uid,
    role = role
)

fun CheckUsernameResponse.toDomain() = CheckUsernameResult(
    exists = exists
)

fun AuthResponse.toDomain() = AuthResult(
    user = user.toDomain(),
    token = token,
    refreshToken = refreshToken
)