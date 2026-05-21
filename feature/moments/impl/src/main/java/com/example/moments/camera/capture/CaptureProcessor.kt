package com.example.moments.camera.capture

import android.graphics.Bitmap
import android.graphics.Matrix

object CaptureProcessor {
    fun process(
        rawBitmap: Bitmap,
        sensorOrientation: Int,
        isFrontCamera: Boolean,
        zoomRatio: Float
    ): Bitmap {

        val matrix = Matrix().apply {

            postRotate(
                sensorOrientation.toFloat()
            )

            if (isFrontCamera) {
                postScale(-1f, 1f)
            }
        }

        val rotated = Bitmap.createBitmap(
            rawBitmap,
            0,
            0,
            rawBitmap.width,
            rawBitmap.height,
            matrix,
            true
        )

        return if (zoomRatio <= 1f) {

            rotated

        } else {

            val cropWidth =
                (rotated.width / zoomRatio).toInt()

            val cropHeight =
                (rotated.height / zoomRatio).toInt()

            val cropX =
                (rotated.width - cropWidth) / 2

            val cropY =
                (rotated.height - cropHeight) / 2

            Bitmap.createBitmap(
                rotated,
                cropX,
                cropY,
                cropWidth,
                cropHeight
            )
        }
    }
}