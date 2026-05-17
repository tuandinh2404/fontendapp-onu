package com.example.onu

import android.app.Application
import com.example.common.coroutine.di.ApplicationScope
import com.example.datastore.session.SessionManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class OnuApplication: Application() {
    @Inject lateinit var sessionManager: SessionManager
    @Inject @ApplicationScope lateinit var scope : CoroutineScope

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            sessionManager.tokenFlow.collect{}
        }
    }

}