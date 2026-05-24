//package com.example.moments.component
//
//import android.content.Context
//import android.graphics.Bitmap
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.FastOutSlowInEasing
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.designsystem.theme.BlackAmoled
//import com.example.moments.CameraViewModel
//import com.example.moments.camera.controller.CameraController
//
//@Composable
//fun CameraShutter(
//    context: Context,
//    controller: CameraController,
//    isCaptured: Boolean,
//    onCapture: (Float) -> Unit,
//    onPhotoTaken: (Bitmap) -> Unit,
//    zoomRatio: Float,
//    viewModel : CameraViewModel = hiltViewModel(),
//    onHoldStart: () -> Unit,
//    onHoldEnd: () -> Unit,
//) {
//    var isPressed by remember { mutableStateOf(false) }
//    var isReleased by remember { mutableStateOf(false) }
//    var pressScale by remember { mutableStateOf(1f) }
//
//
//    val scale by animateFloatAsState(
//        targetValue = when {
//            isPressed -> 0.8f
//            isReleased -> 0.90f
//            else -> 1f
//        },
//        animationSpec = tween(400),
//    )
//    val color by animateColorAsState(
//        targetValue = if (isPressed) Color.Gray.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.5f),
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = FastOutSlowInEasing
//        ),
//        label = ""
//    )
//    Box(
//        Modifier
//            .size(90.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        if (viewModel.isProcessingCapture) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.5f)),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    color = Color.White,
//                    modifier = Modifier
//                        .size(50.dp)
//                )
//            }
//        } else {
//            Box(
//                Modifier
//                    .scale(scale)
//                    .size(75.dp)
//                    .pointerInput(zoomRatio) {
//                        detectTapGestures(
//                            onPress = {
//                                isPressed = true
//                                val released = tryAwaitRelease()
//                                isPressed = false
//                                if (released) {
//                                    onCapture(zoomRatio)
//                                    controller.capture(
//                                        zoomRatio = zoomRatio,
//                                        onFinal = { final ->
//                                            final?.let(onPhotoTaken)
//                                        }
//                                    )
//                                }
//                            },
//                        )
//                    }
//                    .background(color, CircleShape)
//            )
//        }
//        Box(
//            Modifier
//                .fillMaxSize()
//                .border(
//                    width = 3.dp,
//                    color = BlackAmoled.copy(alpha = 0.5f),
//                    shape = CircleShape
//                )
//        )
//
//    }
//}