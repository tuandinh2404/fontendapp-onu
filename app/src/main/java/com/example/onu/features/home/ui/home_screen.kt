package com.example.onu.features.home.ui

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.time.LocalTime
import com.example.onu.R
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home_screen(
    mainController: NavHostController
) {
    val isDay = LocalTime.now().hour in 6..18
    val backgroundColor = if (isDay) Color.White else Color.Black


    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HeaderItem()
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .background(Color.Blue)
            ) {
//                    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
//                        scope.launch {
//                            scale.snapTo((scale * zoomChange).coerceIn(1f, 5f))
//
//                            val extraWidth = (scale - 1) * constraints.maxWidth
//                            val extraHeight = (scale - 1) * constraints.maxHeight
//
//                            val maxX = extraWidth / 2
//                            val maxY = extraHeight / 2
//
//                            offsetX.snapTo(
//                                offset.value + scale.value * offsetChange.x).coerceIn(-maxX, maxX)
//                            )
//                            offsetY.snapTo(
//                                offset.value + scale.value * offsetChange.y).coerceIn(-maxY, maxY)
//                            )
//                        }
//                    }
                AsyncImage(
                    model = R.drawable.anhhh,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
//                            .graphicsLayer {
//                                scaleX = scale.value
//                                scaleY = scale.value
//                                translationX = offsetX.value
//                                translationY = offsetY.value
//                            }
//                            .pointerInput(Unit) {
//                                forEachGesture {
//                                    awaitPointerEventScope {
//
//                                        awaitFirstDown()
//
//                                        do {
//                                            val event = awaitPointerEvent()
//
//                                            val zoom = event.calculateZoom()
//                                            val pan = event.calculatePan()
//
//                                            scope.launch {
//                                                // ---- Zoom ----
//                                                val newScale = (scale.value * zoom).coerceIn(1f, 5f)
//                                                scale.snapTo(newScale)
//
//                                                // ---- Pan giới hạn theo scale ----
//                                                val extraWidth = (newScale - 1f) * constraints.maxWidth
//                                                val extraHeight = (newScale - 1f) * constraints.maxHeight
//
//                                                val maxX = extraWidth / 2f
//                                                val maxY = extraHeight / 2f
//
//                                                offsetX.snapTo(
//                                                    (offsetX.value + pan.x).coerceIn(-maxX, maxX)
//                                                )
//                                                offsetY.snapTo(
//                                                    (offsetY.value + pan.y).coerceIn(-maxY, maxY)
//                                                )
//                                            }
//
//                                        } while (event.changes.any { it.pressed })
//
//                                        // 👉 Khi thả tay — animate trở về trạng thái ban đầu
//                                        scope.launch {
//                                            scale.animateTo(1f)
//                                            offsetX.animateTo(0f)
//                                            offsetY.animateTo(0f)
//                                        }
//                                    }
//                                }
//                            },
                    ,
                    contentScale = ContentScale.Crop
                )
                Box(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(
                            top = 10.dp,
                            start = 10.dp
                        )

                ) {
                    Row(
                        Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        //Thay thành avatar
                        Box(
                            Modifier
                                .size(40.dp)
                                .background(Color.Red, CircleShape)
                        )
                        Box(

                        ) {
                            Text(
                                text = "tuandinh24",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun HeaderItem(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            Modifier
                .background(Color.Green, RoundedCornerShape(15.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "Header",
                    color = Color.White
                )
            }
        }
    }
}