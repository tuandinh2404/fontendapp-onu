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

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = tween(
            easing = LinearEasing,
            durationMillis = 300
        ),
        snapPositionalThreshold = 0.35f
    )
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var isGridMode by remember { mutableStateOf(false) }
    val isMomentsPage by remember {
        derivedStateOf { pagerState.currentPage == 0 }
    }



    VerticalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        userScrollEnabled = false
    ) { page ->
        when(page) {
            0 -> moments_screen(
                mainController = mainController,
                builderController = builderController,
                onOpen = onOpen,
                isCaptured = isCaptured,
                onCapture = onCapture,
                onPhotoTaken = onPhotoTaken,
                isHomeActive = isHomeActive
            )
            1 -> messenger_screen(
                onBack = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
        }
    }
}