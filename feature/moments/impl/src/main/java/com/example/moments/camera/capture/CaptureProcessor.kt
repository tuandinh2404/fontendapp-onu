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
                sensorOrientation.toFloat() + 180f
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
        val cropped = cropToAspectRatio(
            rotated,
            16f / 9f
        )

        return if (zoomRatio <= 1f) {

            cropped

        } else {

            val cropWidth =
                (cropped.width / zoomRatio).toInt()

            val cropHeight =
                (cropped.height / zoomRatio).toInt()

            val cropX =
                (cropped.width - cropWidth) / 2

            val cropY =
                (cropped.height - cropHeight) / 2

            Bitmap.createBitmap(
                cropped,
                cropX,
                cropY,
                cropWidth,
                cropHeight
            )
        }
    }

    private fun cropToAspectRatio(
        bitmap: Bitmap,
        targetRatio: Float
    ): Bitmap {

        val currentRatio =
            bitmap.width.toFloat() / bitmap.height.toFloat()

        var newWidth = bitmap.width
        var newHeight = bitmap.height

        if (currentRatio > targetRatio) {

            // quá rộng
            newWidth =
                (bitmap.height * targetRatio).toInt()

        } else {

            // quá cao
            newHeight =
                (bitmap.width / targetRatio).toInt()
        }

        val x =
            (bitmap.width - newWidth) / 2

        val y =
            (bitmap.height - newHeight) / 2

        return Bitmap.createBitmap(
            bitmap,
            x,
            y,
            newWidth,
            newHeight
        )
    }
}