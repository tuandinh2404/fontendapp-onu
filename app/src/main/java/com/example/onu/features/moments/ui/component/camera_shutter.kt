package com.example.onu.features.moments.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun camera_shutter(
    context: Context,
    controller: camera_controller,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var isReleased by remember { mutableStateOf(false) }
    var pressScale by remember { mutableStateOf(1f) }


    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.8f
            isReleased -> 0.95f
            else -> 1f
        },
        animationSpec = tween(400),
    )
    val color by animateColorAsState(
        targetValue = if (isPressed) Color.Gray.copy(alpha = 0.5f) else Color.Yellow,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )
    Box(
        Modifier
            .size(90.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .scale(scale)
                .size(75.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            val released = tryAwaitRelease()
                            isPressed = false
                            if (released) {
                                onCapture(pressScale)
                                controller.capture(
                                    onFinal = { final ->
                                        final?.let(onPhotoTaken)
                                    }
                                )
                            }
                        },
                    )
                }
                .background(color, CircleShape)
        )
        Box(
            Modifier
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
        )
    }
}

//private fun takePhoto(
//    controller: LifecycleCameraController,
//    onPhotoTaken: (Bitmap) -> Unit,
//    context: Context
//) {
//    controller.takePicture(
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageCapturedCallback() {
//            override fun onCaptureSuccess(image: ImageProxy) {
//                super.onCaptureSuccess(image)
//                onPhotoTaken(image.toBitmap())
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                super.onError(exception)
//                Log.e("Camera", "Error taking photo", exception)
//            }
//        }
//    )
//
//}