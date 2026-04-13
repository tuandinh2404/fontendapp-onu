package com.example.onu.router.MainRouter

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.DpOffset
import com.example.designsystem.icon.OnuIcons
import com.example.onu.R
import com.example.onu.ui.theme.DarkGray
import com.example.onu.ui.theme.LightGray
import com.example.onu.ui.utils.BottomNavigationIcon

private data class NavItem(
    val tab: MainTab,
    val iconActive: Int,
    val iconInactive: Int,
)

private val navItems = listOf(
    NavItem(MainTab.Home, OnuIcons.HomeFill, OnuIcons.HomeBold),
    NavItem(MainTab.Friend, OnuIcons.Friends, OnuIcons.Friends),
    NavItem(MainTab.Notification, OnuIcons.Notification, OnuIcons.Notification)
)

@Composable
fun BottomNavigation(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
    openBottomSheet: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current


    Box(
        modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
//        Box(
//            Modifier
//                .weight(0.8f)
//                .fillMaxHeight(),
//            contentAlignment = Alignment.Center
//
//        ) {
//            Box(
//                Modifier
//                    .size(30.dp)
//                    .clickable(
//                    ) {
//                        haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
//
//                        openBottomSheet()
//                    }
//                    .background(Color.Blue, CircleShape)
//
//            )
//        }
        Row(
            Modifier
                .fillMaxWidth(0.7f)
                .height(60.dp)
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 2.dp,
                        offset = DpOffset(0.dp, 0.dp),
                        color = LightGray.copy(alpha = 0.2f)
                    )
                )
                .clip(CircleShape)
                .background(DarkGray.copy(alpha = 0.8f)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isActive = currentTab == item.tab
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) {
                            if (isActive && item.tab == MainTab.Home) {
                                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                return@clickable
                            } else {
                                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                onTabSelected(item.tab)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BottomNavigationIcon(
                            idIcon = if (isActive) {
                                item.iconActive
                            } else {
                                item.iconInactive
                            },
                            sizeIcon = 30,
                            colorIcon = if (isActive) {
                                LightGray
                            } else {
                                Color.Gray
                            },
                        )
                    }

                }
            }
        }
        val profileInteraction = remember { MutableInteractionSource() }

//        Box(
//            Modifier
//                .weight(0.8f)
//                .fillMaxHeight()
//                .clickable(
//                    indication = null,
//                    interactionSource = profileInteraction
//                ) {
//                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
//
//                    openBottomSheet()
//                },
//            contentAlignment = Alignment.Center
//
//        ) {
//            Box(
//                Modifier
//                    .size(30.dp)
//                    .background(Color.Blue, CircleShape)
//
//            )
//        }
    }
}
