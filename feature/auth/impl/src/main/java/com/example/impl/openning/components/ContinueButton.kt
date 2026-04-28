package com.example.impl.openning.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.icon.OnuImages
import com.example.designsystem.theme.BlackAmoled
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.LightPeachBeige
import com.example.designsystem.theme.NunitoFontFamily
import com.example.designsystem.theme.WarmTan
import com.example.impl.openning.OpenningViewModel

@Composable
fun ContinueButton(
    scale: Float,
    hasInteracted: Boolean,
    goToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = hasInteracted,
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it },
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { it }
        ),
        modifier = modifier

    ) {
        Box(
            Modifier
                .fillMaxWidth(0.9f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        goToRegister()
                    }
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 8.dp,
                            color = BlackAmoled.copy(alpha = 0.4f),
                            offset = DpOffset(0.dp, 0.dp)
                        )
                    )
                    .background(LightGray, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
//            Icon(
//                painter = painterResource(OnuIcons.ArrowRight),
//                contentDescription = null,
//                tint = DarkGray,
//                modifier = Modifier.size(40.dp)
//            )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Làm tiếp nhé ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        fontFamily = NunitoFontFamily

                    )
                    Icon(
                        painter = painterResource(OnuIcons.Star),
                        contentDescription = null,
                        tint = DarkGray,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 25.dp, bottom = 15.dp)

            ) {
                Image(
                    painter = painterResource(OnuImages.FlowerRed),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 25.dp, top = 15.dp)

            ) {
                Image(
                    painter = painterResource(OnuImages.FlowerBlue),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }

        }

    }

}