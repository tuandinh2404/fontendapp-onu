package com.example.datastore.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TOKEN = stringPreferencesKey("token")

    val tokenFlow: Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[TOKEN]
        }

    suspend fun getToken(): String?=
        dataStore.data.map { preferences ->
            preferences[TOKEN]
        }.first()

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}