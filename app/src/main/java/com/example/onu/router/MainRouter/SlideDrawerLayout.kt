//package com.example.onu.router.MainRouter
//
//import androidx.compose.animation.Animatable
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.VectorConverter
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.detectHorizontalDragGestures
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//import kotlin.math.roundToInt
//
//@Composable
//fun SlideDrawerLayout(
//    drawerContent: @Composable () -> Unit,
//    mainContent: @Composable () -> Unit,
//) {
//    val configuration = LocalConfiguration.current
//    val density = LocalDensity.current
//    val drawerWidthFraction = 0.7f
//    val screenWidth = configuration.screenWidthDp.dp
//    val drawerWidthPx = with(density) { (screenWidth * drawerWidthFraction).toPx() }
//
//    val offsetX = remember { Animatable(0f) }
//    val scope = rememberCoroutineScope()
//
//    val overlayAlpha = (offsetX.value / drawerWidthPx) * 0.6f
//
//    Box(
//        Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectHorizontalDragGestures(
//                    onHorizontalDrag = { _, dragAmount ->
//                        scope.launch {
//                            offsetX.snapTo(
//                                (offsetX.value + dragAmount).coerceIn(0f, drawerWidthPx)
//                            )
//                        }
//                    },
//                    onDragEnd = {
//                        scope.launch {
//                            if (offsetX.value > drawerWidthPx * 0.4f) {
//                                offsetX.animateTo(drawerWidthPx)
//                            } else {
//                                offsetX.animateTo(0f)
//                            }
//                        }
//                    }
//                )
//            }
//    ) {
//        Box(
//            Modifier
//                .fillMaxHeight()
//                .width(screenWidth * drawerWidthFraction)
//                .background(Color(0xFF202020))
//        ) {
//            drawerContent()
//        }
//
//        Box(
//            Modifier
//                .fillMaxSize()
//                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
//                .shadow(if (offsetX.value > 0) 16.dp else 0.dp)
//                .clip(RoundedCornerShape(if (offsetX.value > 0) 16.dp else 0.dp))
//
//        ) {
//            mainContent()
//        }
//
//        if (overlayAlpha > 0) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
//                    .background(Color.Black.copy(alpha = overlayAlpha))
//                    .pointerInput(Unit) {
//                        detectTapGestures {
//                            scope.launch { offsetX.animateTo(0f) }
//                        }
//                    }
//            )
//        }
//    }
//}