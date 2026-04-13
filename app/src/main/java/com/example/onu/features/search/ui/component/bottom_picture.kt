package com.example.onu.features.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun botton_picture(

) {

    Box(
        Modifier
            .size(100.dp)
            .border(
                width = 5.dp,
                color = Color.Cyan,
                shape = CircleShape
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(90.dp)
                .background(Color.White, CircleShape)
        )
    }
}