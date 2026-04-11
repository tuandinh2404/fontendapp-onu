package com.example.onu.core.ui.overlay.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
private val PixelCyan = Color(0xFF5CE6F5)
private val PixelBorder    = Color(0xFF000000)
private val PixelShadow    = Color(0xFF2B8FAD)
private val PixelHighlight = Color(0xFFFFFFFF)

@Composable
fun PixelSpeechBubble(
    text: String,
    modifier: Modifier = Modifier,
    px: Dp = 4.dp,
    fillColor: Color = PixelCyan,
) {
    // wrapContentSize → co theo nội dung
    Column(modifier = modifier.wrapContentSize(), horizontalAlignment = Alignment.Start) {

        // ── Main bubble ──────────────────────────────────
        Box(modifier = Modifier.wrapContentSize()) {
            // Shadow (offset 1px right+down)
            Box(
                modifier = Modifier
                    .offset(x = px, y = px)
                    .align(Alignment.TopStart)
            ) {
                BubbleBody(text = text, px = px, fillColor = PixelShadow, textColor = PixelShadow)
            }

            BubbleBody(text = text, px = px, fillColor = fillColor, textColor = Color.Black)
        }

        // ── Tail ─────────────────────────────────────────
        Box(
            Modifier
                .padding(start = px * 6)
                .size(px * 3)
                .background(PixelBorder)
        ) {
            Box(
                Modifier
                    .padding(start = px, top = px)
                    .size(px)
                    .background(fillColor)
            )
        }

        Box(
            Modifier
                .padding(start = px * 2)
                .size(px * 3)
                .background(PixelBorder)
        ) {
            Box(
                Modifier
                    .padding(start = px, top = px)
                    .size(px)
                    .background(fillColor)
            )
        }
    }
}

@Composable
private fun BubbleBody(
    text: String,
    px: Dp,
    fillColor: Color,
    textColor: Color,
) {
    // IntrinsicSize.Min → Column co lại theo Row rộng nhất (= theo text)
    Column(modifier = Modifier.width(IntrinsicSize.Min)) {
        // top border
        Box(
            Modifier
                .fillMaxWidth()
                .height(px)
                .background(PixelBorder)
        )

        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // left border
            Box(Modifier.width(px).fillMaxHeight().background(PixelBorder))

            // inner fill
            Box {
                Box(Modifier.matchParentSize().background(fillColor))

                // highlight stripe
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(px)
                        .background(PixelHighlight.copy(alpha = 0.45f))
                )

                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = px * 5, vertical = px * 3),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    letterSpacing = 1.sp,
                    softWrap = false,   // không xuống dòng, bubble dài theo text
                )
            }

            // right border
            Box(Modifier.width(px).fillMaxHeight().background(PixelBorder))
        }

        // bottom border
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.width(px).height(px).background(PixelBorder))
            Box(Modifier.weight(1f).height(px).background(PixelShadow))
            Box(Modifier.width(px).height(px).background(PixelShadow))
        }
    }
}

