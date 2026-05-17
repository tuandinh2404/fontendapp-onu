package com.example.impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun friend_topbar(
    modifier : Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
    ) {
        item_bar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun item_bar() {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = "Today",
                    fontSize = 20.sp,
                    color = LightGray,
                    fontWeight = FontWeight.Bold

                )
            }
            Row(

            ) {

            }
        }
    }
}