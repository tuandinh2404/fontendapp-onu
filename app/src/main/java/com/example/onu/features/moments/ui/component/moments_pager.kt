package com.example.onu.features.moments.ui.component

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onu.MainActivity
import com.example.onu.features.moments.CameraViewModel

@Composable
fun moments_pager(
    mainController: NavHostController,
    builderController: NavHostController,
    isCaptured: Boolean,
    onCapture: (Float) -> Unit,
    onPhotoTaken: (Bitmap) -> Unit,
    viewModel: CameraViewModel = viewModel(),
    isHomeActive: Boolean
) {
    val context = LocalContext.current
    val hasPermissions = hasRequiredPermissions(context)
    val cameraController = remember { camera_controller(context) }
//    DisposableEffect(isHomeActive) {
//        if (isHomeActive) {
//            cameraController.resumeAnalysis()
//        } else {
//            cameraController.pauseAnalysis()
//        }
//        onDispose {
//            cameraController.pauseAnalysis()
//        }
//    }

    Column(
        Modifier
            .padding(top = 120.dp)
    ) {
        if(isHomeActive) {



            if (!hasPermissions) {
                camera_permission_screen()
            } else {
                camera_screen(
                    controller = cameraController.controller,
                    cameraController = cameraController
                )
            }
        } else {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Black)
            )
        }
        camera_controls(
            builderController = builderController,
            controller = cameraController,
            context = context,
            isCaptured = isCaptured,
            onCapture = onCapture,
            onPhotoTaken = onPhotoTaken
        )
    }
}

@Composable
fun camera_permission_screen(

) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 5f)
            .background(Color.Black, RoundedCornerShape(15.dp))
    )
}

fun hasRequiredPermissions(context: Context): Boolean {
    return MainActivity.CAMERAX_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

