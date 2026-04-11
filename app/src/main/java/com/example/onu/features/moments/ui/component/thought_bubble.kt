package com.example.onu.features.moments.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun thought_bubble(
) {
    val density = LocalDensity.current
    val tipHeight = with(density) { 14.dp.toPx() }
    val startPadding = with(density) { 15.dp.toPx() }
    val tipWidth = with(density) { 12.dp.toPx() }
    val radius = with(density) { 10.dp.toPx() }

    Box(
        Modifier

    ) {
        Box(
            Modifier
                .width(70.dp)
                .height(50.dp)
                .drawBehind {
                    val bubbleHeight = size.height - tipHeight
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                left = 0f,
                                top = 0f,
                                right = size.width,
                                bottom = size.height - tipHeight,
                                radiusX = radius,
                                radiusY = radius
                            )
                        )
                        moveTo(
                            x = startPadding,
                            y = bubbleHeight
                        )
                        quadraticBezierTo(
                            x1 = startPadding + tipWidth / 2,
                            y1 = bubbleHeight + tipHeight,
                            x2 = startPadding + tipWidth,
                            y2 = bubbleHeight
                        )
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color.Red
                    )
                }
        )
    }
}