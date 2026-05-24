package com.example.onu.router.MainRouter

import android.view.TextureView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray
import com.example.impl.bottom_sheet_profile
import com.example.impl.friend_screen
import com.example.impl.notify_screen
import com.example.moments.CameraViewModel
import com.example.moments.camera.controller.CameraController
import com.example.moments.detail.MomentsPreviewScreen
import com.example.onu.router.MainRouter.home_navigation.home_nav
import kotlinx.coroutines.launch
import kotlin.OptIn
import kotlin.math.abs
import kotlin.text.get

enum class MainTab {
    Notification,
    Home,
    Friend,
}
private data class MainUIState(
    val currentTab: MainTab = MainTab.Home,
    val showBottomSheet: Boolean = false
)

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Main(
    builderController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel(),

    ) {
    val context = LocalContext.current
    val mainController = rememberNavController()

    val cameraController = remember { CameraController(context) }

    var uiState by remember { mutableStateOf(MainUIState()) }
    var isOpened by remember { mutableStateOf(false) }
    var onCloseAction by remember { mutableStateOf<() -> Unit>({}) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isTabSwitching by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()

    val tabs = MainTab.entries
    val pagerState = rememberPagerState(
        initialPage = tabs.indexOf(MainTab.Home),
        pageCount = { tabs.size })

    LaunchedEffect(pagerState.currentPage) {
        uiState = uiState.copy(currentTab = tabs[pagerState.currentPage])
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            userScrollEnabled = !viewModel.isCaptured,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (tabs[page]) {
                MainTab.Notification -> notify_screen()
                MainTab.Home -> home_nav(
                    mainController = mainController,
                    builderController = builderController,
                    onOpen = { closeAction ->
                        isOpened = true
                        onCloseAction = closeAction
                    },
                    isCaptured = viewModel.isCaptured,
                    onCapture = { pressScale ->
                        viewModel.onCapture(pressScale)
                    },
                    onPhotoTaken = { bitmap ->
                        viewModel.onPhotoTaken(bitmap)

                    },
                    isHomeActive = pagerState.settledPage == page && !viewModel.isOpenedPreview,
                    currentTab = tabs[pagerState.currentPage] == MainTab.Home,
                )

                MainTab.Friend -> friend_screen(
                    isScrollEnabled = !pagerState.isScrollInProgress
                )
            }
        }
        if (uiState.showBottomSheet) {
            bottom_sheet_profile(
                sheetState,
                builderController,
                mainController,
                showBottomSheet = uiState.showBottomSheet,
                onUpClickBottomSheet = { uiState = uiState.copy(showBottomSheet = false) },
            )
        }

        BottomNavigation(
            currentTab = uiState.currentTab,
            onTabSelected = { newTab ->
                val index = tabs.indexOf(newTab)
                val current = pagerState.currentPage
                scope.launch {
                    if(abs(current - index) > 1 ) {
                        isTabSwitching = true
                        pagerState.scrollToPage(index)
                        isTabSwitching = false
                    } else {
                        pagerState.animateScrollToPage(index)
                    }
                }
                uiState = uiState.copy(currentTab = newTab)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
                .zIndex(2f),
            openBottomSheet = { uiState = uiState.copy(showBottomSheet = true) },
            pagerState = pagerState,
            onShutterClick = { viewModel.requestCapture() },
            isTabSwitching = isTabSwitching,
            isEnabled = !viewModel.isCaptured
        )
        if (viewModel.isOpenedPreview) {
            BackHandler {
                viewModel.closePreview()
            }
            MomentsPreviewScreen(
                viewModel = viewModel,
                onFetchLocation = { viewModel.fetchCurrentLocation() },
                onClosed = { viewModel.closePreview() }
            )
        }
    }
}