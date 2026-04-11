package com.example.onu.features.friends.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun friends_topBar(
    modfier: Modifier = Modifier
) {
    Box(
        modfier
            .fillMaxWidth()
    ) {
        itemBar()
    }

}

@Composable
 private fun itemBar(

 ) {
     Box(
         Modifier
             .fillMaxWidth()
             .height(100.dp)
             .background(Color(0xFFf1f1f0))
     ) {
         Row(
             Modifier
                 .fillMaxSize(),
             horizontalArrangement = Arrangement.SpaceBetween,
             verticalAlignment = Alignment.CenterVertically
         ) {
             Box(
                 Modifier
                     .padding(
                         start = 20.dp,
                         top = 30.dp
                     )
             ) {
                 Text(
                     text = "Bạn bè",
                     fontSize = 30.sp,
                     fontWeight = FontWeight.Bold,
                     color = Color.Black
                 )
             }

         }


     }


 }