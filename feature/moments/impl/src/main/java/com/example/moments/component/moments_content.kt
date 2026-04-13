package com.example.moments.component

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun moments_content(
    mainController: NavHostController,
    builderController: NavHostController,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean
) {
    moments_pager(
        mainController = mainController,
        builderController = builderController,
        isCaptured = isCaptured,
        onCapture = onCapture,
        onPhotoTaken = onPhotoTaken,
        isHomeActive = isHomeActive
    )

}