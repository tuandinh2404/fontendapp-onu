package com.example.moments.camera.preview

import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCharacteristics
import android.util.Size
import kotlin.math.abs

object PreviewSizeSelector {
    fun select(
        characteristics: CameraCharacteristics
    ): Size {

        val streamMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        val sizes = streamMap?.getOutputSizes(SurfaceTexture::class.java) ?: return android.util.Size(1920, 1080)

        return sizes
            .filter {
                val ratio = maxOf(it.width, it.height).toFloat() /
                        minOf(it.width, it.height).toFloat()

                abs(ratio - (16f / 9f)) < 0.01f
            }
            .maxByOrNull { it.width * it.height }
            ?: android.util.Size(1920, 1080)
    }
}