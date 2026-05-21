package com.example.moments.component

import android.hardware.lights.Light
import android.view.TextureView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.JosefinSansFontFamily
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.NunitoFontFamily
import com.example.designsystem.theme.OswaldFontFamily
import com.example.moments.camera.controller.CameraController


@Composable
fun CameraScreen(
    cameraController: CameraController,
    onTextureReady: (TextureView) -> Unit,
    flipCamera:() -> Unit,
    onZoomChanged: (Float) -> Unit,
    zoomRatio: Float,
    holdProgressProvider:() -> Float,
    time: String
) {

    var isFlashCamera by remember { mutableStateOf(false) }
    val isFront by cameraController.isFrontCamera.collectAsState()
    val maxZoom = remember(isFront) { cameraController.getMaxZoom() }
    var currentZoomIndex by remember { mutableStateOf(0) }
    val currentZoomRatio by rememberUpdatedState(zoomRatio)





    LaunchedEffect(isFront) {
        currentZoomIndex = 0
        onZoomChanged(1f)
    }


    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        CameraControlsBar(
            cameraController = cameraController,
            isFlashCamera = isFlashCamera,
            flashCamera = { isFlashCamera = !isFlashCamera },
            zoomRatio = zoomRatio
        )
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.90f)
                    .pointerInput(maxZoom) {
                        detectTransformGestures { _, _, zoom, _ ->
                            val newZoom = (currentZoomRatio * zoom).coerceIn(1f, maxZoom)
                            onZoomChanged(newZoom)
                        }
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                flipCamera()
                            }
                        )
                    }
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(DarkGray)
                    .border(
                        width = 2.dp,
                        color = Color.Cyan,
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                CameraPreview(
                    modifier = Modifier
                        .fillMaxSize(),
                    onTextureAvailable = { textureCamera ->
                        onTextureReady(textureCamera)
                        cameraController.openCamera(textureCamera)
                    },
                    onSurfaceDestroyed = {
                        cameraController.release()
                    }
                )
                CropOverlay(
                    zoomRatio = zoomRatio,
                    holdProgressProvider = holdProgressProvider
                )
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 40.dp)
                ) {
                    Text(
                        text = time,
                        color = LightGray,
                        modifier = Modifier
                            .rotate(90f),
                        fontSize = 45.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = JosefinSansFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun CameraControlsBar(
    cameraController: CameraController,
    isFlashCamera: Boolean,
    flashCamera: () -> Unit,
    zoomRatio: Float
) {
    Box(
        Modifier
            .fillMaxWidth(0.93f)
            .fillMaxHeight(0.70f)
            .clip(RoundedCornerShape(15.dp))
            .background(DarkGray),
    ) {
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
                        flashCamera()
                        cameraController.toggleFlash()
                    }
            )

        }
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        ) {
            Box(
                Modifier
                    .size(30.dp)
                    .background(DarkGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(24 * zoomRatio).toInt()}",
                    color = LightGray
                )
            }

        }
    }
}