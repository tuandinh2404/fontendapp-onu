package com.example.onu.features.moments.ui.component
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment

data class FloatingIcon(
    val id: Long,
    val emoji: String,
    val startX: Float,       // 0f..1f (tỉ lệ theo width màn hình)
    val size: Float,         // sp
    val duration: Int,       // ms
    val driftX: Float,       // lệch X trong quá trình bay
    val wobbleX: Float,      // lắc lư thêm ở cuối
    val delayMs: Long = 0L, // ✅ delay lệch nhau

)

@Composable
fun FloatingIconOverlay(
    icons: List<FloatingIcon>,
    onRemove: (Long) -> Unit,
    modifier : Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        icons.forEach { icon ->
            key(icon.id) {
                FloatingIconItem(
                    icon = icon,
                    onFinished = { onRemove(icon.id) },
                )
            }
        }
    }
}

@Composable
private fun FloatingIconItem(
    icon: FloatingIcon,
    onFinished: () -> Unit,
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { config.screenHeightDp.dp.toPx() }

    // Bay từ bottom lên trên đầu màn hình
    val offsetY = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.7f) }

    LaunchedEffect(icon.id) {
        delay(icon.delayMs) // ✅ icon bay rải ra, không cùng lúc
        launch {
            // Fade in nhanh
            alpha.animateTo(1f, tween(150))
            // Giữ rồi fade out ở 80% quãng đường
            delay((icon.duration * 0.75).toLong())
            alpha.animateTo(0f, tween((icon.duration * 0.25).toInt()))
        }
        launch {
            scale.animateTo(
                targetValue = 0.85f + Random.nextFloat() * 0.3f,
                animationSpec = tween(icon.duration, easing = EaseOut),
            )
        }
        launch {
            offsetY.animateTo(
                targetValue = -(screenHeightPx + 100f),
                animationSpec = tween(icon.duration, easing = EaseInOut),
            )
        }
        delay(icon.duration.toLong())
        onFinished()
    }

    val startXPx = screenWidthPx * icon.startX
    // Drift nhẹ sang trái/phải trong lúc bay
    val driftFraction = (-offsetY.value / (screenHeightPx + 100f)).coerceIn(0f, 1f)
    val currentX = startXPx + icon.driftX * driftFraction + icon.wobbleX * driftFraction

    Text(
        text = icon.emoji,
        fontSize = icon.size.sp,
        modifier = Modifier
            .offset {
                IntOffset(
                    x = currentX.toInt(),
                    y = (screenHeightPx + offsetY.value).toInt(),
                )
            }
            .alpha(alpha.value)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                rotationZ = icon.driftX * 0.20f * driftFraction
            },
    )
}

// Helper để tạo batch icons mỗi lần nhấn thích
fun spawnFloatingIcons(
    emoji: String,
    currentIcons: List<FloatingIcon>,
    count: Int = 12,
    maxIcons: Int = 60,
): List<FloatingIcon> {
    val time = System.nanoTime() // ✅ nanoTime tránh trùng id
    val newIcons = List(count) { i ->
        FloatingIcon(
            id = time + i,
            emoji = emoji,
            startX = Random.nextFloat(),                          // random vị trí X
            size = 32f + Random.nextFloat() * 48f,               // size 18sp–38sp
            duration = 1800 + Random.nextInt(1400),              // 1.8s–3.2s
            driftX = (Random.nextFloat() - 0.5f) * 120f,        // lệch trái/phải
            wobbleX = (Random.nextFloat() - 0.5f) * 60f,        // lắc lư nhẹ
            delayMs = i * 80L + Random.nextLong(60), // ✅ rải đều
        )
    }
    return (currentIcons + newIcons).takeLast(maxIcons)

}