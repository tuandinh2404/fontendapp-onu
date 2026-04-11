package com.example.onu.features.moments.ui.component

import android.app.Activity
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

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        WindowInsetsControllerCompat(window, view)
            .isAppearanceLightStatusBars = darkIcons

        onDispose {
            window.statusBarColor = oldColor
            WindowInsetsControllerCompat(window, view)
                .isAppearanceLightStatusBars = oldAppearance
        }
    }
}