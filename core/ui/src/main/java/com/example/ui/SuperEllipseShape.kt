package com.example.ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin

class SuperEllipseShape(
    private val n: Float = 4.5f // 4.0–5.0 giống iOS / Snapchat
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val a = size.width / 2f
        val b = size.height / 2f

        val steps = 360

        for (i in 0..steps) {
            val t = Math.toRadians(i.toDouble()).toFloat()

            val cosT = cos(t)
            val sinT = sin(t)

            val x = a * sign(cosT) * abs(cosT).pow(2f / n)
            val y = b * sign(sinT) * abs(sinT).pow(2f / n)

            if (i == 0) {
                path.moveTo(a + x, b + y)
            } else {
                path.lineTo(a + x, b + y)
            }
        }

        path.close()
        return Outline.Generic(path)
    }
}