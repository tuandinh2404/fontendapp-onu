package com.example.moments.list
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.designsystem.theme.BlackAmoled
import com.example.designsystem.theme.DarkGray
import com.example.moments.CameraViewModel
import com.example.moments.component.moments_content
import com.example.moments.component.moments_statusbar_effect
import com.example.ui.permission.OnuPermission
import com.example.ui.permission.rememberPermissionState
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun moments_screen(
    mainController: NavHostController,
    builderController: NavHostController,
    onOpen: (() -> Unit) -> Unit,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val cameraPermission = rememberPermissionState(OnuPermission.CAMERA)
    LaunchedEffect(isHomeActive) {
        if(!cameraPermission.isGranted && isHomeActive) {
            awaitFrame()
            cameraPermission.request()
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(BlackAmoled)
    ) {
        if(showBottomSheet) {
            BottomSheetMedia(
                sheetState = sheetState,
                onUpClickBottomSheet = { showBottomSheet = false }
            )

        }
        moments_statusbar_effect(darkIcons = true)

        moments_content(
            mainController = mainController,
            builderController = builderController,
            isCaptured = isCaptured,
            onCapture = onCapture,
            onPhotoTaken = onPhotoTaken,
            isHomeActive = isHomeActive,
            onOpenBottomSheet = { showBottomSheet = true },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMedia(
    sheetState: SheetState,
    onUpClickBottomSheet: () -> Unit
) {
    val state = rememberLazyListState()

    ModalBottomSheet(
        onDismissRequest = {
            onUpClickBottomSheet()
        },
        sheetState = sheetState,
        dragHandle = {

        },
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }

    ) {
        Box(
         Modifier
             .fillMaxSize()
             .background(BlackAmoled)
        )
    }
}