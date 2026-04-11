package com.example.moments.list

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
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
            .background()
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
//        moments_topbar(
//            modifier = Modifier
//                .align(Alignment.TopCenter),
//            progress = { progress },
//            onOpen = {
//                onOpen {
//                    offsetY = 0f
//                    hasNavigated = false
//                }
//            }
//        )
    }
}