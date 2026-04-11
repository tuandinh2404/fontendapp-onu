package com.example.onu.features.moments.ui

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.onu.R
import com.example.onu.features.moments.ui.component.moments_content
import com.example.onu.features.moments.ui.component.moments_statusbar_effect
import com.example.onu.features.moments.ui.component.moments_topbar
import com.example.onu.ui.theme.DarkGray
import kotlinx.coroutines.launch


data class PageItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val backgroundColor: List<Int>,
    val accentColor: Color,
    val name: String
)

// ViewModel
class PagerViewModel : ViewModel() {
    private val imageList = listOf(
        R.drawable.anhhh,
        R.drawable.anh1,
        R.drawable.anh2,
        R.drawable.anh3,
        R.drawable.anh4,
    )
    private val nameUser = listOf(
        "Alexxander",
        "Romeoooo",
        "Violett",
        "RonaldoooCr7",
        "Joinnyyy"
    )
    val items = List(imageList.size) { index ->
        PageItem(
            id = index + 1,
            title = "Trang ${index + 1}",
            subtitle = "Vuốt lên để xem trang tiếp theo",
            backgroundColor = imageList,
            accentColor = Color.White,
            name = nameUser[index]
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun moments_screen(
    mainController: NavHostController,
    builderController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    sheetState: SheetState,
    onOpen: (() -> Unit) -> Unit,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean
) {
    val scope = rememberCoroutineScope()
    var offsetY by remember { mutableFloatStateOf(0f) }
    var hasNavigated by remember { mutableStateOf(false) }
    val maxDrag = 300f
    val progress = (offsetY / maxDrag).coerceIn(0f, 1f)
    val haptic = LocalHapticFeedback.current


    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        if (hasNavigated) return@detectVerticalDragGestures

                        val newOffset = (offsetY + dragAmount)
                            .coerceAtLeast(0f)

                        offsetY = newOffset
                        val newProgress = (newOffset / maxDrag)
                            .coerceIn(0f, 1f)

                        if (newProgress > 0.4f) {
                            hasNavigated = true
                            haptic.performHapticFeedback(
                                HapticFeedbackType.LongPress
                            )
                            onOpen {
                                offsetY = 0f
                                hasNavigated = false
                            }
                        }
                    },
                    onDragEnd = {
                        if (!hasNavigated) {
                            scope.launch { offsetY = 0f }
                        }
                    }
                )
            }
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
        moments_topbar(
            modifier = Modifier
                .align(Alignment.TopCenter),
            progress = { progress },
            onOpen = {
                onOpen {
                    offsetY = 0f
                    hasNavigated = false
                }
            }
        )
    }
}








