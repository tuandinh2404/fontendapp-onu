package com.example.moments.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons

@Composable
fun button_moments(
    builderController: NavHostController,
    onUpClick: () -> Unit,
    isGridMode: Boolean,
    colorButton: Brush,
    backgroundColor: Color
) {
    val heightPx = with(LocalDensity.current) { 60.dp.toPx() }

    val width by animateDpAsState(
        targetValue = if (isGridMode) 70.dp else 300.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "ratio"
    )
    val height by animateDpAsState(
        targetValue = if (isGridMode) 70.dp else 70.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "height"
    )
    val corner by animateDpAsState(
        targetValue = if (isGridMode) 35.dp else 35.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "corner"
    )
    val bgcolor by animateColorAsState(
        targetValue = if(isGridMode) Color.Blue else Color.Cyan,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "bgcolor"
    )
    Box(
        Modifier
            .width(width)
            .height(height)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onUpClick()

            }
            .background(brush = colorButton, RoundedCornerShape(corner)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isGridMode == false,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                Text(
                    text = "new moments",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
                Icon(
                    painter = painterResource(OnuIcons.Add),
                    contentDescription = "add moments",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
        AnimatedVisibility(
            visible = isGridMode == true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .size(60.dp)
                    .background(backgroundColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }

    }
}
