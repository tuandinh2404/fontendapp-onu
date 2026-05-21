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

        val scaleX = viewWidth / bufferWidth    // 1080/2448
        val scaleY = viewHeight / bufferHeight  // 1440/3264

        // Lấy scale lớn hơn để fill
        val scale = maxOf(scaleX, scaleY)

        val matrix = Matrix()
        matrix.postScale(
            scale / scaleY,  // compensate
            scale / scaleX,
            centerX,
            centerY
        )
        textureView.setTransform(matrix)

    }
}