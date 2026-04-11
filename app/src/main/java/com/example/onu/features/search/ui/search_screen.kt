package com.example.onu.features.search.messenger_detail

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.onu.R
import com.example.onu.features.search.ui.component.botton_picture
import com.example.onu.features.search.ui.component.camera_module

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search_screen(
    mainController: NavHostController,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity


    var isOn by remember { mutableStateOf(false) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val requestCameraPermission = {
        when {
            hasCameraPermission -> {
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            ) -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                openAppSettings(context)
            }
        }
    }
    var showPermissionDialog by remember { mutableStateOf(false) }

    if(showPermissionDialog) {
        AlertDialog(
            onDismissRequest = {
                showPermissionDialog = false
            },
            title = {
                Text(text = "Cần quyền truy cập")
            },
            text = {
                Text("Ứng dụng cần quyền camera để hoạt động")
            },
            confirmButton = {
                Text(
                    "Mở cài đặt",
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                        showPermissionDialog = false
                        openAppSettings(context)
                    }
                )
            },



        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
            )

        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                camera_module(
                    isOn = isOn,
                    onToggle = { isOn = it },
                    hasCameraPermission,
                    openAppSettings = { showPermissionDialog = true }
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            Modifier
                                .size(100.dp),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                        botton_picture()
                        Box(
                            Modifier
                                .width(100.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrows_rotate),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }


        }

    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}


