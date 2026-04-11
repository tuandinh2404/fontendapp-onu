package com.example.onu.router.MainRouter

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.onu.R
import com.example.onu.core.ui.overlay.frame.PixelSpeechBubble
import com.example.onu.core.ui.overlay.frame.TornEdges
import com.example.onu.core.ui.overlay.frame.TornPaperNote
import com.example.onu.features.Notification.ui.notify_screen
import com.example.onu.features.friends.ui.friend_screen
import com.example.onu.features.moments.CameraViewModel
import com.example.onu.features.moments.ui.PagerViewModel
import com.example.onu.features.moments.ui.component.bottom_sheet_profile
import com.example.onu.features.profile.ui.profile_screen
import com.example.onu.features.view_more.ui.view_more
import com.example.onu.router.MainRouter.home_navigation.home_nav
import com.example.onu.router.NavigationBuilder_Router
import com.example.onu.ui.theme.DarkGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.OptIn
import kotlin.math.abs

enum class MainTab {
    Home,
    Friend,
    Notification,
}
private data class MainUIState(
    val currentTab: MainTab = MainTab.Home,
    val showBottomSheet: Boolean = false
)

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Main(
    builderController: NavHostController,
    viewModel: CameraViewModel = viewModel(),

    ) {
    val mainController = rememberNavController()

    var uiState by remember { mutableStateOf(MainUIState()) }
    var isOpened by remember { mutableStateOf(false) }
    var onCloseAction by remember { mutableStateOf<() -> Unit>({}) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scope = rememberCoroutineScope()
//    val scale by animateFloatAsState(
//        targetValue = if (isCaptured) 0.6f else 1f,
//        animationSpec = tween(400),
//    )


    Box(
        Modifier
            .fillMaxSize()
    ) {
        MainTab.entries.forEach { tab ->
            val isActive = uiState.currentTab == tab
            Box(
                Modifier
                    .fillMaxSize()
                    .alpha(if (isActive) 1f else 0f)
                    .zIndex(if (isActive) 1f else 0f)
                    .then(
                        if(!isActive) Modifier.pointerInput(Unit) {}
                        else Modifier
                    )
            ) {
                when (tab) {
                    MainTab.Home ->  home_nav(
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
                        isHomeActive = uiState.currentTab == MainTab.Home && !isOpened
                    )
                    MainTab.Friend -> friend_screen()
                    MainTab.Notification -> notify_screen()
                }
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
                uiState = uiState.copy(currentTab = newTab)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f),
            openBottomSheet = { uiState = uiState.copy(showBottomSheet = true) },


        )
        AnimatedVisibility(
            visible = isOpened,
            enter = fadeIn(tween(200)) + scaleIn(
                tween(100),
                initialScale = 0f,
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            ),
            exit = fadeOut(tween(200)),
            modifier = Modifier
                .zIndex(10f)
        ) {
            BackHandler {
                isOpened = false
                onCloseAction()
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)  // ← Thay bằng màn hình bạn muốn
            ) {
                // Nội dung màn messenger_detail ở đây
                // messenger_detail_screen(...)

                // Nút back
                Box(
                    Modifier
                        .padding(top = 50.dp, start = 20.dp)
                        .size(40.dp)
                        .background(Color.LightGray, CircleShape)
                        .clickable {
                            isOpened = false
                            onCloseAction()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_tab), // thay icon
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        }
        if(viewModel.isOpenedPreview) {
            BackHandler {
                viewModel.closePreview()
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .zIndex(10f)
                    .pointerInput(Unit) {}
                    .background(DarkGray)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f/5f)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    viewModel.capturedBitmap?.let { bitmap ->
                        AsyncImage(
                            model = viewModel.capturedBitmap,
                            contentDescription = "Captured Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp)
                    ) {
                        PixelSpeechBubble(
                            text = "Game Over",
                            fillColor = Color(0xFFFF6B6B),   // pixel red
                        )
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Box(
                        Modifier
                            .padding(start = 6.dp)
                    ) {
                        Box(
                            Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}