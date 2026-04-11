package com.example.onu.features.moments.ui.component

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.onu.features.moments.ui.PageItem

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