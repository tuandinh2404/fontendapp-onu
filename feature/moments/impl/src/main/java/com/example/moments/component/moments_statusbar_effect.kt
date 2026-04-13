package com.example.moments.component

import android.app.Activity
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun moments_statusbar_effect(
    darkIcons: Boolean = false
) {
    val view = LocalView.current
    val activity = view.context as Activity
    val window = activity.window
    DisposableEffect(Unit) {
        val oldColor = window.statusBarColor
        val oldAppearance =
            WindowInsetsControllerCompat(window, view)
                .isAppearanceLightStatusBars

        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, view)
            .isAppearanceLightStatusBars = darkIcons

        onDispose {
            window.statusBarColor = oldColor
            WindowInsetsControllerCompat(window, view)
                .isAppearanceLightStatusBars = oldAppearance
        }
    }
}