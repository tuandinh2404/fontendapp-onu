package com.example.moments.camera.preview

import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCharacteristics
import android.util.Size

object PreviewSizeSelector {
    fun select(
        characteristics: CameraCharacteristics
    ): Size {

        val streamMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        val sizes = streamMap?.getOutputSizes(SurfaceTexture::class.java) ?: return android.util.Size(1080, 1440)

        return sizes
            .filter {
                kotlin.math.abs(
                    (it.width.toFloat() / it.height.toFloat()) - (4f / 3f)
                ) < 0.01f
            }
            .filter { it.width <= 1920 }
            .maxByOrNull { it.width * it.height }
            ?: android.util.Size(1080, 1440)
    }
}