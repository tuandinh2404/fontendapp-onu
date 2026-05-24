package com.example.moments.camera.preview

import android.graphics.Matrix
import android.util.Size
import android.view.TextureView

object PreviewTransform {
    fun apply(
        textureView: TextureView,
        previewSize: Size
    ) {
        val viewWidth = textureView.width.toFloat()   // 1080
        val viewHeight = textureView.height.toFloat() // 1440

        val centerX = viewWidth / 2f
        val centerY = viewHeight / 2f

        // Sau rotate 90°: buffer width↔height đổi chỗ
        // buffer thật sau rotate: 2448 wide x 3264 tall
        val bufferWidth = previewSize.height.toFloat()  // 2448
        val bufferHeight = previewSize.width.toFloat()  // 3264

        val scale = maxOf(
            viewWidth / bufferWidth,
            viewHeight / bufferHeight
        )

        val scaleX = bufferWidth * scale    // 1080/2448
        val scaleY = bufferHeight * scale  // 1440/3264

        // Lấy scale lớn hơn để fill

        val matrix = Matrix()
        matrix.postScale(
            scaleX / viewWidth,
            scaleY / viewHeight,
            centerX,
            centerY
        )
        textureView.setTransform(matrix)

    }
}