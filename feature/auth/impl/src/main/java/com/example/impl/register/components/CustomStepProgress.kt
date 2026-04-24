package com.example.impl.register.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.LightPeachBeige
import com.example.designsystem.theme.WarmTan


@Composable
fun CustomStepProgress(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    val progressWidth = currentStep.toFloat()  / totalSteps.toFloat()
    val animatedProgressWidth by animateFloatAsState(
        targetValue = progressWidth,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "progressAnimation"
    )

    Box(
        modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.9f)
                .height(3.dp)
                .background(Color.Gray, CircleShape)
        ) {
            Box(
                Modifier
                    .fillMaxWidth(animatedProgressWidth)
                    .fillMaxHeight()
                    .background(WarmTan, CircleShape)

            )
        }
    }
}