package com.example.onu.features.moments.ui.component

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.onu.ui.theme.DarkGray
import com.example.onu.R
import com.example.onu.router.NavigationBuilder_Router

@Composable
fun camera_controls(
    builderController: NavHostController,
    context: Context,
    controller: camera_controller,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit
) {
    var rotation by remember { mutableStateOf(0f) }

    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        label = ""
    )

    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    builderController.navigate(NavigationBuilder_Router.messenger_detail.route)
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
            onPhotoTaken = onPhotoTaken
        )
        Box(
            Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.rotate),
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
                        controller.flipCamera()
                    }
            )
        }
    }
}