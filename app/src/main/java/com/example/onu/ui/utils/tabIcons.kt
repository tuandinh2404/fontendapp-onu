package com.example.onu.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.onu.R

@Composable
fun BottomNavigationIcon(
    idIcon: Int,
    sizeIcon: Int,
    colorIcon: Color,
) {
    Icon(
        painter = painterResource(idIcon),
        contentDescription = null,
        modifier = Modifier
            .size(sizeIcon.dp),
        tint = colorIcon
    )
}