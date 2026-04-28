package com.example.onu.router.MainRouter.home_navigation

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.moments.list.moments_screen
import kotlinx.coroutines.launch
import com.example.impl.messenger_screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun home_nav(
    mainController: NavHostController,
    builderController: NavHostController,
    onOpen: (() -> Unit) -> Unit,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean
    ) {
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    moments_screen(
        mainController = mainController,
        builderController = builderController,
        onOpen = onOpen,
        isCaptured = isCaptured,
        onCapture = onCapture,
        onPhotoTaken = onPhotoTaken,
        isHomeActive = isHomeActive
    )
}