package com.example.onu.router.MainRouter

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.DpOffset
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.BlackAmoled
import com.example.onu.R
import com.example.onu.ui.theme.DarkGray
import com.example.onu.ui.theme.LightGray
import com.example.onu.ui.utils.BottomNavigationIcon
import kotlin.math.abs

private data class NavItem(
    val tab: MainTab,
    val iconActive: Int,
    val iconInactive: Int,
)

private val navItems = listOf(
    NavItem(MainTab.Notification, OnuIcons.User, OnuIcons.User),
    NavItem(MainTab.Friend, OnuIcons.Friends, OnuIcons.Friends),
)

@Composable
fun BottomNavigation(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
    openBottomSheet: () -> Unit,
    pagerState: PagerState,
    onShutterClick: () -> Unit,
    isTabSwitching: Boolean,
    isEnabled: Boolean
) {
    val haptic = LocalHapticFeedback.current
    val homeIndex = MainTab.entries.indexOf(MainTab.Home)

    val distance = abs(
        pagerState.currentPage -
                homeIndex +
                pagerState.currentPageOffsetFraction
    )

    val alpha = if(isTabSwitching) 0f else (1f - distance).coerceIn(0f, 1f)

    val scale = 0.7f + (0.3f * alpha)

    val iconAlpha = 1f - alpha


    Box(
        modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {

        Row(
            Modifier
                .fillMaxWidth(0.6f)
                .height(70.dp)
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 2.dp,
                        offset = DpOffset(0.dp, 0.dp),
                        color = BlackAmoled.copy(alpha = 0.5f)
                    )
                )
                .clip(CircleShape)
                .background(DarkGray),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabItem(
                navItems[0],
                currentTab == navItems[0].tab,
                haptic,
                modifier = Modifier
                    .weight(1f),
                enabled = isEnabled
            ) {
                onTabSelected(navItems[0].tab)
            }
            Box(
                Modifier
                .weight(1f)
                .fillMaxHeight()
                    .graphicsLayer {
                        this.alpha = iconAlpha
                    },
                contentAlignment = Alignment.Center
            ) {
                BottomNavigationIcon(
                    idIcon = OnuIcons.Camera,
                    sizeIcon = 30,
                    colorIcon = if (currentTab == MainTab.Home) LightGray else Color.Gray,
                )
            }
            TabItem(
                navItems[1],
                currentTab == navItems[1].tab,
                haptic,
                modifier = Modifier
                    .weight(1f),
                enabled = isEnabled
            ) {
                onTabSelected(navItems[1].tab)
            }
        }
        Box(
            Modifier
                .size(60.dp)
                .graphicsLayer {

                    this.alpha = alpha

                    scaleX = scale
                    scaleY = scale
                }
                .clip(CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if(currentTab == MainTab.Home && alpha > 0.95f) {
                        onShutterClick()
                    } else {
                        onTabSelected(MainTab.Home)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Transparent,  CircleShape)
                    .border(
                        width = 2.dp,
                        color = Color.Cyan,
                        shape = CircleShape
                    )
            )
            Box(
                Modifier
                    .size(48.dp)
                    .background(LightGray,  CircleShape)
            )
        }
    }
}

@Composable
private fun TabItem(
    item: NavItem,
    isActive: Boolean,
    haptic: HapticFeedback,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxHeight()
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        BottomNavigationIcon(
            idIcon = if (isActive) item.iconActive else item.iconInactive,
            sizeIcon = 20,
            colorIcon = if (isActive) LightGray else Color.Gray,
        )
    }
}
