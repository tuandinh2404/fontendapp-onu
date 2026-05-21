package com.example.network.auth

interface TokenProvider {
    fun getAccessToken(): String?
}