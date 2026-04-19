package com.example.network.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,

    @SerializedName("full_name")
    val fullName: String,
    val uid: String,
    val role: String?
)