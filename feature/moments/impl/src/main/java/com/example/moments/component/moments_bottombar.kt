package com.example.moments.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun moments_bottombar(
    modifier: Modifier = Modifier,
    isGridMode: Boolean,
    builderController: NavHostController,
    onUpClick: () -> Unit,
    colorButton: Brush,
    backgroundColor: Color

) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AnimatedVisibility(
            visible = isGridMode == false,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = FastOutLinearInEasing
                )

            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = FastOutLinearInEasing
                )
            )
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center

            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .background(Color.Gray, RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                        ))
                )
            }
        }
        button_moments(
            builderController,
            onUpClick,
            isGridMode,
            colorButton,
            backgroundColor
        )
    }
}