package com.example.moments.component

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.ui.permission.OnuPermission
import com.example.ui.permission.rememberPermissionState

@Composable
fun camera_controls(
    builderController: NavHostController,
    textureViewRef: () -> Unit,
    context: Context,
    controller: CameraController,
    zoomRatio: Float,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    onOpenBottomSheet: () -> Unit,
    onHoldStart: () -> Unit,
    onHoldEnd: () -> Unit
) {
    var rotation by remember { mutableStateOf(0f) }
    var isPressed by remember { mutableStateOf(false) }


    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        label = ""
    )
    val scale by animateFloatAsState(
        targetValue = if(isPressed) 0.8f else 1f
    )
    val mediaPermission = rememberPermissionState(OnuPermission.MEDIA_IMAGES)


    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            val released = tryAwaitRelease()
                            isPressed = false
                            if (released) {
                                if (mediaPermission.isGranted) {
                                    onOpenBottomSheet()
                                } else {
                                    mediaPermission.request()
                                }
                            }
                        },
                    )
                }
        ) {
            Box(
                Modifier
                    .padding(
                        top = 5.dp,
                        start = 15.dp
                    )
            ) {
                Box(
                    Modifier
                        .width(35.dp)
                        .aspectRatio(4f / 5f)
                        .graphicsLayer {
                            rotationZ = 9.toFloat()
                        }
                        .background(Color.Red.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                )
            }
            Box(
                Modifier
                    .width(40.dp)
                    .aspectRatio(4f/5f)
                    .background(Color.Cyan, RoundedCornerShape(10.dp))

            )
        }
        camera_shutter(
            controller = controller,
            context = context,
            isCaptured = isCaptured,
            onCapture = onCapture,
            onPhotoTaken = onPhotoTaken,
            zoomRatio = zoomRatio,
            onHoldStart = onHoldStart,
            onHoldEnd = onHoldEnd
        )
        Box(
            Modifier
        ) {
            Icon(
                painter = painterResource(OnuIcons.Rotate),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        rotationZ = animatedRotation
                    }
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        rotation += 180f
                        textureViewRef()
                    }
            )
        }
    }
}