package com.example.data.auth

import com.example.datastore.session.SessionManager
import com.example.network.auth.TokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatastoreTokenProvider @Inject constructor(
    private val seessionManager: SessionManager
): TokenProvider {
    override fun getAccessToken(): String? {
        return seessionManager.cachedToken
    }
}