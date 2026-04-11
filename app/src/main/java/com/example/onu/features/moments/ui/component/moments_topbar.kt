package com.example.onu.features.moments.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.onu.R
import com.example.onu.ui.theme.DarkGray
import com.example.onu.ui.theme.LightGray

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun moments_topbar(
    modifier : Modifier = Modifier,
    progress:() -> Float,
    onOpen: () -> Unit,
) {
    Box(
        modifier
            .fillMaxWidth()
    ) {
        item_bar(
            progress,
            onOpen = onOpen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun item_bar(
    progress:() -> Float,
    onOpen: () -> Unit) {

//    val scale = 1f + (progress * 0.4f)
//    val translateY = progress * 200f

    Box(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
            ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .background(Color.Blue,CircleShape)
            )
            Column(
                Modifier
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val p = progress()  // chỉ đọc ở đây
                            scaleX = 1f + (p * 0.4f)
                            scaleY = 1f + (p * 0.4f)
                            translationY = p * 200f
                        }
                        .zIndex(1f)
                        .clickable(

                        ) {
                            onOpen()
                        }
                ) {

                    val shapee = RoundedCornerShape(5.dp)

                    val items = listOf(
                        Triple(Color.Red, -10.dp, -9f),
                        Triple(Color.Black, 0.dp, 0f),
                        Triple(Color.Blue, 10.dp, 9f)
                    )

                    items.forEachIndexed { index, (color, offsetX, rotation) ->

                        Box(
                            Modifier
                                .zIndex(index.toFloat())
                                .width(30.dp)
                                .aspectRatio(4f / 5f)
                                .graphicsLayer {
                                    val p = progress()
                                    translationX = (offsetX + offsetX * p).toPx()
                                    rotationZ = rotation * p
                                    shadowElevation = 8.dp.toPx()
                                    shape = shapee
                                    clip = true
                                }
                                .background(color)
                        )
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.angle_double_down),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Box(
                Modifier
                    .size(40.dp)
                    .background(Color.Blue,CircleShape)
            )
        }
    }
}