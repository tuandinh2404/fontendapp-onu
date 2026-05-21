package com.example.onu.router.MainRouter.home_navigation

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moments.list.MomentsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun home_nav(
    mainController: NavHostController,
    builderController: NavHostController,
    onOpen: (() -> Unit) -> Unit,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean,
    ) {
    MomentsScreen(
        mainController = mainController,
        builderController = builderController,
        onOpen = onOpen,
        isCaptured = isCaptured,
        onCapture = onCapture,
        onPhotoTaken = onPhotoTaken,
        isHomeActive = isHomeActive,

    )
}