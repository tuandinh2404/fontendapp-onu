package com.example.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun messenger_topBar(
    onBack: () -> Unit

) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        itemBar(onBack)
    }

}

@Composable
private fun itemBar(
    onBack: () -> Unit
) {
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
            Box(
                Modifier
                    .width(160.dp)
                    .height(40.dp)
                    .background(Color.Blue, CircleShape)
            )
            Box(
                Modifier
                    .size(40.dp)
                    .background(Color.Blue,CircleShape)
            )
        }
    }
}