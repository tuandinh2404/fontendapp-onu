package com.example.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.designsystem.icon.OnuIcons

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun friend_topbar(
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
                    painter = painterResource(OnuIcons.AngleDoubleDown),
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