package com.example.onu.features.Notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.onu.features.Notification.ui.components.notify_topBar

@Composable
fun notify_screen(

) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        notify_topBar()

    }
}