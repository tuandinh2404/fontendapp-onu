package com.example.impl.register.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = enabled && !isLoading,
        enter = fadeIn(),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { it }
        ),
        modifier = modifier
            .padding(bottom = 20.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .height(55.dp)
                .clip(RoundedCornerShape(15.dp))
                .clickable(
                    enabled = enabled && !isLoading,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() }
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 8.dp,
                        color = DarkGray.copy(alpha = 0.4f),
                        offset = DpOffset(0.dp, 0.dp)
                    )
                )
                .background(LightGray)
        )
    }
}