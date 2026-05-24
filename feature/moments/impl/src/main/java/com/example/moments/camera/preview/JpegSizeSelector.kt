package com.example.moments.camera.preview

import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.util.Log
import android.util.Size
import kotlin.math.abs

object JpegSizeSelector {
    fun select(
        characteristics: CameraCharacteristics
    ): Size {
        val streamMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: return Size(1920, 1080)
        val jpegSizes =
            streamMap.getOutputSizes(ImageFormat.JPEG)

        jpegSizes.forEach {
            Log.d(
                "JPEG_SIZE",
                "${it.width} x ${it.height}"
            )
        }

        val selected = jpegSizes
            .filter {

                val ratio =
                    maxOf(it.width, it.height).toFloat() /
                            minOf(it.width, it.height).toFloat()

                abs(ratio - (16f / 9f)) < 0.02f
            }
            .maxByOrNull { it.width * it.height }
            ?: jpegSizes.maxByOrNull { it.width * it.height }
            ?: Size(1920, 1080)
        Log.d("JPEG_SIZE", "selected = ${selected.width} x ${selected.height}")
        return selected
    }
}