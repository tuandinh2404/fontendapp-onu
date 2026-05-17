package com.example.moments.component

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightBlueGray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.NavyBlack

@Composable
fun CropOverlay(
    zoomRatio: Float,
    holdProgressProvider: () -> Float = { 0f }
) {
    val lightGrayArgb = LightGray.toArgb()
    val lightBlueGray = LightBlueGray.toArgb()

    val textPaint = remember {
        Paint().apply {
            color = lightGrayArgb
            isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
        }
    }
//    val progressPaint = remember {
//        Paint().apply {
//            color = lightBlueGray
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//            strokeCap = Paint.Cap.ROUND
//        }
//    }
//    val dst = remember { android.graphics.Path() }
//    val progressPath = remember { android.graphics.Path() }
//    val pathMeasure = remember { android.graphics.PathMeasure() }



    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
    ) {
        val cropW = size.width / zoomRatio
        val cropH = size.height / zoomRatio
        val cropX = (size.width - cropW) / 2f
        val cropY = (size.height - cropH) / 2f

        drawRect(DarkGray.copy(alpha = 0.6f))

        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(cropX, cropY),
            size = Size(cropW, cropH),
            cornerRadius = CornerRadius(15.dp.toPx()),
            blendMode = BlendMode.Clear
        )

        drawRoundRect(
            color = if(zoomRatio == 1f) Color.Transparent else LightGray,
            topLeft = Offset(cropX, cropY),
            size = Size(cropW, cropH),
            cornerRadius = CornerRadius(15.dp.toPx()),
            style = Stroke(width = 2.dp.toPx()),

        )
        if (zoomRatio != 1f) {
            textPaint.textSize = 12.dp.toPx()
            val mmText = "${(24 * zoomRatio).toInt()}mm"
            drawContext.canvas.nativeCanvas.drawText(
                mmText,
                cropX + 10.dp.toPx(),
                cropY - 8.dp.toPx(),
                textPaint
            )
        }
//        val holdProgress = holdProgressProvider()
//        if (holdProgress > 0f) {
//
//            val stroke = 6.dp.toPx()
//            val radius = 15.dp.toPx()
//
//            progressPaint.apply {
//                strokeWidth = stroke
//                style = Paint.Style.STROKE
//                strokeCap = Paint.Cap.ROUND
//                strokeJoin = Paint.Join.ROUND
//            }
//
//            val left = stroke / 2
//            val top = stroke / 2
//            val right = size.width - stroke / 2
//            val bottom = size.height - stroke / 2
//
//            progressPath.reset()
//            progressPath.apply {
//                moveTo(left + (right - left) / 2f, top)            // top-center
//                lineTo(right - radius, top)                         // → top-right
//                arcTo(android.graphics.RectF(right - radius * 2, top, right, top + radius * 2), 270f, 90f, false)
//                lineTo(right, bottom - radius)                      // → bottom-right
//                arcTo(android.graphics.RectF(right - radius * 2, bottom - radius * 2, right, bottom), 0f, 90f, false)
//                lineTo(left + radius, bottom)                       // → bottom-left
//                arcTo(android.graphics.RectF(left, bottom - radius * 2, left + radius * 2, bottom), 90f, 90f, false)
//                lineTo(left, top + radius)                          // → top-left
//                arcTo(android.graphics.RectF(left, top, left + radius * 2, top + radius * 2), 180f, 90f, false)
//                lineTo(left + (right - left) / 2f, top)            // → top-center
//            }
//
//            pathMeasure.setPath(progressPath, false)
//            val stop = pathMeasure.length * holdProgress
//
//            dst.reset()
//            pathMeasure.getSegment(0f, stop, dst, true)
//
//            drawContext.canvas.nativeCanvas.drawPath(dst, progressPaint)
//        }

    }
}