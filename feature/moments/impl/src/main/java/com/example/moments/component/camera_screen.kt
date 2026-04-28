package com.example.moments.component

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.designsystem.icon.OnuIcons


@Composable
fun camera_screen(
    controller: LifecycleCameraController,
    cameraController: camera_controller
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isFlashCamera by remember { mutableStateOf(false) }


    DisposableEffect(Unit) {
        onDispose {
            cameraController.pauseAnalysis()
        }
    }
    LaunchedEffect(Unit) {
        controller.bindToLifecycle(lifecycleOwner)
        cameraController.startAnalisis()

    }
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 5f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Blue)
    ) {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            cameraController.flipCamera()
                        }
                    )
                }
        )
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .padding(start = 15.dp, bottom = 15.dp)
        ) {
            Icon(
                painter =
                    if (!isFlashCamera) painterResource(OnuIcons.FlashOff)
                    else painterResource(OnuIcons.FlashOn),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isFlashCamera = !isFlashCamera
                        cameraController.toggleFlash()
                    }
            )

        }
    }
}