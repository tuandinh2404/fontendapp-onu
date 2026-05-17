package com.example.ui.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberPermissionState(
    permission: OnuPermission,
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
): PermissionState {
    val context = LocalContext.current
    var isGranted by remember {
        mutableStateOf(hasPermissions(context, permission))
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        isGranted = granted
        if (granted) onGranted() else onDenied()
    }

    val refresh = {
        isGranted = hasPermissions(context, permission)
    }

   return PermissionState(
        isGranted = isGranted,
        request = { launcher.launch(permission.permission) },
        refresh = refresh
    )
}

data class PermissionState(
    val isGranted: Boolean,
    val request: () -> Unit,
    val refresh: () -> Unit
)