package com.example.moments.camera.utils

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log

object CameraLogger {
    fun logAllCameras(
        cameraManager: CameraManager
    ) {

        for (id in cameraManager.cameraIdList) {

            val characteristics =
                cameraManager.getCameraCharacteristics(id)

            val facing =
                characteristics.get(
                    CameraCharacteristics.LENS_FACING
                )

            val focalLengths =
                characteristics.get(
                    CameraCharacteristics
                        .LENS_INFO_AVAILABLE_FOCAL_LENGTHS
                )

            val maxZoom =
                characteristics.get(
                    CameraCharacteristics
                        .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM
                )

            Log.d(
                "CameraInfo",
                """
                ID: $id
                facing: $facing
                focal: ${focalLengths?.toList()}
                maxZoom: $maxZoom
                """.trimIndent()
            )
        }
    }
}