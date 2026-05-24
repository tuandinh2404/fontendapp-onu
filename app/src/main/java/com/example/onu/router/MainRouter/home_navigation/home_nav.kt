package com.example.onu.router.MainRouter.home_navigation

import android.graphics.Bitmap
import android.view.TextureView
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray
import com.example.moments.list.MomentsScreen
import com.example.onu.router.MainRouter.MainTab

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
    currentTab: Boolean,
) {

    MomentsScreen(
        mainController = mainController,
        builderController = builderController,
        onOpen = onOpen,
        isCaptured = isCaptured,
        onCapture = onCapture,
        onPhotoTaken = onPhotoTaken,
        isHomeActive = isHomeActive,
        currentTab = currentTab
    )
}