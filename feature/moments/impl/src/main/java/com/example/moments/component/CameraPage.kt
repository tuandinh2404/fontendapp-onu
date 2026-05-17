package com.example.moments.component

import android.graphics.Bitmap
import android.view.TextureView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.designsystem.theme.BlackAmoled
import com.example.designsystem.theme.LightGray
import com.example.moments.CameraViewModel
import com.example.ui.permission.OnuPermission
import com.example.ui.permission.openAppSettings
import com.example.ui.permission.rememberPermissionState

@Composable
fun CameraPage(
    mainController: NavHostController,
    builderController: NavHostController,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    isHomeActive: Boolean,
    onOpenBottomSheet: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(OnuPermission.CAMERA)
    val cameraController = remember { CameraController(context) }
    var zoomRatio by remember { mutableFloatStateOf(1f) }

    var textureViewRef by remember { mutableStateOf<TextureView?>(null) }

    var isHolding by remember { mutableStateOf(false) }
    val holdProgress by animateFloatAsState(
        targetValue = if (isHolding) 1f else 0f,
        animationSpec = if (isHolding) tween(3000, easing = LinearEasing) else tween(200),
        label = ""
    )
    val weather by viewModel.weather.collectAsState()



    LaunchedEffect(isHomeActive) {
        if(isHomeActive) {
            cameraPermission.refresh()
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(BlackAmoled)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
            ) {
                weather?.let {
                    Text(
                        text = "${it.temp}°C",
                        color = LightGray,
                        fontSize = 20.sp
                    )
                }

            }

        }
        if(isHomeActive) {
            if (!cameraPermission.isGranted) {
                camera_permission_screen(
                    onRequestPermission = {
                        context.openAppSettings()
                    }
                )
            } else {
                camera_screen(
                    cameraController = cameraController,
                    onTextureReady   = { textureViewRef = it },
                    flipCamera = { textureViewRef?.let { cameraController.flipCamera(it) } },
                    onZoomChanged = { zoomRatio = it },
                    zoomRatio = zoomRatio,
                    holdProgressProvider = { holdProgress },
                )
            }
        } else {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(BlackAmoled)
            )
        }
        camera_controls(
            builderController = builderController,
            textureViewRef = { textureViewRef?.let { cameraController.flipCamera(it) } },
            controller = cameraController,
            zoomRatio = zoomRatio,
            context = context,
            isCaptured = isCaptured,
            onCapture = { onCapture(zoomRatio)},
            onPhotoTaken = onPhotoTaken,
            onOpenBottomSheet = onOpenBottomSheet,
            onHoldStart = { isHolding = true },
            onHoldEnd = { isHolding = false }
        )
    }
}

@Composable
fun camera_permission_screen(
    onRequestPermission: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 5f)
            .background(Color.Black, RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onRequestPermission()
                }
                .background(Color.Red)
        ) {
            Text(
                text = "Xin quyền Camera"
            )
        }
    }
}

