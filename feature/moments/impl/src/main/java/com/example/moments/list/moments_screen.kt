package com.example.moments.list

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavHostController
import com.example.moments.component.moments_content
import com.example.moments.component.moments_statusbar_effect

data class PageItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val backgroundColor: List<Int>,
    val accentColor: Color,
    val name: String
)

@Composable
fun moments_screen(
    mainController: NavHostController,
    builderController: NavHostController,
    onOpen: (() -> Unit) -> Unit,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        moments_statusbar_effect(darkIcons = true)

        moments_content(
            mainController = mainController,
            builderController = builderController,
            isCaptured = isCaptured,
            onCapture = onCapture,
            onPhotoTaken = onPhotoTaken,
            isHomeActive = isHomeActive
        )
    }
}