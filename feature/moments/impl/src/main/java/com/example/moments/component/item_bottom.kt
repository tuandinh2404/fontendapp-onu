package com.example.moments.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.zIndex
import com.example.designsystem.icon.OnuIcons


@Composable
fun item_bottom(

) {
    Box(
        Modifier
            .height(100.dp)
        ,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Black, RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
        )
        Box(
            Modifier
                .padding(top = 15.dp)
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center


        ) {
            Box(
                Modifier
                    .size(70.dp)
                    .clip(HalfBottomShape())
                    .background(Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {

            }
            Box(
                Modifier
                    .size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable(

                        ) {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                    Icon(
                        painter = painterResource(OnuIcons.Camera),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .zIndex(1f),
                        tint = Color.Black
                    )
                }
            }
        }
        
    }
}

class HalfBottomShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Bắt đầu từ giữa bên trái
            moveTo(0f, size.height / 2)
            // Đường thẳng xuống góc dưới trái
            lineTo(0f, size.height)
            // Đường thẳng sang góc dưới phải
            lineTo(size.width, size.height)
            // Đường thẳng lên giữa bên phải
            lineTo(size.width, size.height / 2)
            // Đóng path
            close()
        }
        return Outline.Generic(path)
    }
}