package com.example.onu.core.ui.overlay.frame

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import kotlin.random.Random

// ─────────────────────────────────────────────
// Data
// ─────────────────────────────────────────────

data class TornEdges(
    val top: Boolean,
    val right: Boolean,
    val bottom: Boolean,
    val left: Boolean,
)

// ─────────────────────────────────────────────
// Path builder
// ─────────────────────────────────────────────

private fun buildTornPath(
    width: Float,
    height: Float,
    edges: TornEdges,
    random: Random,
): Path {
    val step = 36f
    val path = Path()

    fun tear() = 8f + random.nextFloat() * 12f   // 8–20 px amplitude

    // ── TOP ──────────────────────────────────
    if (edges.top) {
        path.moveTo(0f, tear())
        var x = 0f
        while (x < width) {
            val nx = min(x + step, width)
            val mid = x + (nx - x) * (0.4f + random.nextFloat() * 0.2f)
            val ty = if (random.nextBoolean()) -tear() else tear()
            path.quadraticBezierTo(mid, ty, nx, if (random.nextBoolean()) -tear() else tear())
            x = nx
        }
    } else {
        path.moveTo(0f, 0f)
        path.lineTo(width, 0f)
    }

    // ── RIGHT ─────────────────────────────────
    if (edges.right) {
        var y = 0f
        while (y < height) {
            val ny = min(y + step, height)
            val mid = y + (ny - y) * (0.4f + random.nextFloat() * 0.2f)
            val tx = width + (if (random.nextBoolean()) -tear() else tear())
            path.quadraticBezierTo(tx, mid, width + (if (random.nextBoolean()) -tear() else tear()), ny)
            y = ny
        }
    } else {
        path.lineTo(width, height)
    }

    // ── BOTTOM (right → left) ─────────────────
    if (edges.bottom) {
        var x = width
        while (x > 0f) {
            val nx = maxOf(x - step, 0f)
            val mid = x - (x - nx) * (0.4f + random.nextFloat() * 0.2f)
            val ty = height + (if (random.nextBoolean()) -tear() else tear())
            path.quadraticBezierTo(mid, ty, nx, height + (if (random.nextBoolean()) -tear() else tear()))
            x = nx
        }
    } else {
        path.lineTo(0f, height)
    }

    // ── LEFT (bottom → top) ───────────────────
    if (edges.left) {
        var y = height
        while (y > 0f) {
            val ny = maxOf(y - step, 0f)
            val mid = y - (y - ny) * (0.4f + random.nextFloat() * 0.2f)
            val tx = if (random.nextBoolean()) -tear() else tear()
            path.quadraticBezierTo(tx, mid, if (random.nextBoolean()) -tear() else tear(), ny)
            y = ny
        }
    }

    path.close()
    return path
}

// ─────────────────────────────────────────────
// Random edge combos
// ─────────────────────────────────────────────

private val EDGE_PRESETS = listOf(
    TornEdges(top = true,  right = false, bottom = true,  left = false),
    TornEdges(top = true,  right = true,  bottom = true,  left = true),
    TornEdges(top = true,  right = false, bottom = false, left = true),
    TornEdges(top = false, right = true,  bottom = true,  left = false),
    TornEdges(top = true,  right = true,  bottom = false, left = true),
    TornEdges(top = true,  right = false, bottom = true,  left = true),
)

fun randomTornEdges(seed: Long = System.currentTimeMillis()): TornEdges =
    EDGE_PRESETS[Random(seed).nextInt(EDGE_PRESETS.size)]

// ─────────────────────────────────────────────
// Component
// ─────────────────────────────────────────────

/**
 * A torn-paper style note.
 *
 * @param text       Caption text displayed inside the note
 * @param paperColor Background fill colour of the paper
 * @param edges      Which edges look torn; use [randomTornEdges] for variety
 * @param seed       Random seed used to generate the torn outline
 */
@Composable
fun TornPaperNote(
    text: String,
    modifier: Modifier = Modifier,
    paperColor: Color = Color(0xFFFFF3CD),
    edges: TornEdges = randomTornEdges(),
    seed: Long = 0L,
) {
    Box(modifier = modifier.wrapContentSize()) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height

            // Drop-shadow pass
            val shadowPath = buildTornPath(w, h, edges, Random(seed + 1))
            drawPath(
                path = shadowPath.also { it.translate(Offset(3f, 5f)) },
                color = Color(0x22000000),
            )

            // Paper fill
            val paperPath = buildTornPath(w, h, edges, Random(seed))
            drawPath(path = paperPath, color = paperColor)

            // Ruled lines
            drawRuledLines(w, h)
        }

        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Default,
            color = Color(0xFF1A1A1A),
            lineHeight = 22.sp,
        )
    }
}

// ─────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────

private fun DrawScope.drawRuledLines(width: Float, height: Float) {
    val lineSpacing = 28f
    val lineColor = Color(0x18000000)
    var y = lineSpacing * 2
    while (y < height - lineSpacing) {
        drawLine(
            color = lineColor,
            start = Offset(12f, y),
            end = Offset(width - 12f, y),
            strokeWidth = 1f,
        )
        y += lineSpacing
    }
}

// ─────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────
