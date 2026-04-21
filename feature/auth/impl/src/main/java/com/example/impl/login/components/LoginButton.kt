package com.example.impl.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(
                bottom = 20.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.9f)
                .height(60.dp)
                .clip(CircleShape)
                .background(if (text.isNotEmpty()) LightGray else Color.Gray)
                .clickable(
                    enabled = text.isNotEmpty() && !isLoading,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if(isLoading) {
                CircularProgressIndicator(
                    color = DarkGray,
                    modifier = Modifier
                        .size(30.dp)
                )
            } else {
                Text(
                    text = "Tiếp tục",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (text.isNotEmpty()) DarkGray else LightGray.copy(alpha = 0.2f),
                )
            }
        }
    }

}