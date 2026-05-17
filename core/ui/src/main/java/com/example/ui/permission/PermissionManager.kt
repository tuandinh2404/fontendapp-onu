package com.example.ui.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

enum class OnuPermission(val permission: String) {
    CAMERA(Manifest.permission.CAMERA),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),
    NOTIFICATION(Manifest.permission.POST_NOTIFICATIONS),

    MEDIA_IMAGES(Manifest.permission.READ_MEDIA_IMAGES),
    FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION)
}

fun hasPermission(context: Context, permission: OnuPermission): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission.permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun hasPermissions(context: Context, vararg permissions: OnuPermission): Boolean {
    return permissions.all { hasPermission(context, it) }
}