package com.example.impl.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray

@Composable
fun TopBar(
    goBack:() -> Unit,
    goLogin:() -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            Modifier
                .padding(
                    start = 20.dp
                )
        ) {
            Icon(
                painter = painterResource(OnuIcons.ArrowBack),
                contentDescription = "Back",
                tint = LightGray,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        goBack()
                    }
            )
        }
        Box(
            Modifier
        ) {
            Text(
                text = "moyaa.",
                fontSize = 20.sp,
                color = LightGray,
//                modifier = Modifier
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() }
//                    ) {
//                        goLogin()
//                    }
                fontWeight = FontWeight.ExtraLight
            )
        }
    }
}