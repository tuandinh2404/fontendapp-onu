package com.example.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun OnuTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = OnuTypography,
        content = content
    )
}