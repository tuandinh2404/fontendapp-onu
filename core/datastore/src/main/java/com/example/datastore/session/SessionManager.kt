package com.example.datastore.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TOKEN = stringPreferencesKey("token")

    var cachedToken: String? = null
        private set

    val tokenFlow: Flow<String?> =
        dataStore.data
            .map { preferences ->
            preferences[TOKEN]
        }
            .onEach { cachedToken = it }

    suspend fun getToken(): String?=
        dataStore.data.map { preferences ->
            preferences[TOKEN]
        }.first()

    suspend fun saveToken(token: String) {
        cachedToken = token
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun clear() {
        cachedToken = null
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}