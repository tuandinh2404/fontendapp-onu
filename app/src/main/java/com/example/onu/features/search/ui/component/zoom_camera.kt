package com.example.onu.features.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun zoom_camera(

) {
    Box(
        Modifier
            .size(30.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f), CircleShape)
        ) {
        }
        Box(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "1x",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight(800)
            )
        }
    }
}