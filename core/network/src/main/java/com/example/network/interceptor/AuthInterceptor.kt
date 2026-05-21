package com.example.network.interceptor

import com.example.network.auth.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
): Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val token = tokenProvider.getAccessToken()
        val request = chain.request()
            .newBuilder()
            .apply{
                if (token != null) {
                    addHeader("Authorization", "Bearer $token") }
            }
            .build()
        return chain.proceed(request)
    }
}