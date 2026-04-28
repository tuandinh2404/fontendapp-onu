package com.example.impl.openning

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.BlackAmoled
import com.example.designsystem.theme.CoolGrayBlack
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.DarkTeal
import com.example.designsystem.theme.LightBlueGray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.LightPeachBeige
import com.example.designsystem.theme.NavyBlack
import com.example.impl.openning.OpenningViewModel
import com.example.impl.openning.components.ContinueButton
import com.example.impl.openning.components.OpenningContent
import com.example.impl.openning.components.TopBar
import com.example.impl.openning.components.WheelPicker
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.cos

@Composable
fun OpenningScreen(
    navController: NavHostController,
    goToRegister:() -> Unit ,
    goToLogin: () -> Unit,
    viewModel: OpenningViewModel = viewModel()

) {

    val listStateDay = rememberLazyListState(initialFirstVisibleItemIndex = viewModel.startDay)
    val listStateMonth = rememberLazyListState(initialFirstVisibleItemIndex = viewModel.startMonth)



    LaunchedEffect(listStateDay, listStateMonth) {
        snapshotFlow { listStateDay.isScrollInProgress || listStateMonth.isScrollInProgress }
            .collect { scrolling ->
                if (scrolling) viewModel.onInteracted()
            }
    }

    val scale by animateFloatAsState(
        targetValue = if (viewModel.hasInteracted) 1f else 0.6f,
        animationSpec = tween(300),
        label = "size"
    )

    LaunchedEffect(Unit) {
        delay(100)
        viewModel.onInteracted()
        delay(200)
        viewModel.onContended()
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(BlackAmoled),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            TopBar(
                onGoLogin = goToLogin
            )
            OpenningContent(
                selectedDay = viewModel.selectedDay,
                selectedMonth = viewModel.selectedMonth,
                listStateDay = listStateDay,
                listStateMonth = listStateMonth,
                onChangeDay = { viewModel.onDayChanged(it) },
                onChangeMonth = { viewModel.onMonthChanged(it) },
                hasContended = viewModel.hasContended
            )
        }
        ContinueButton(
            scale = scale,
            hasInteracted = viewModel.hasInteracted,
            goToRegister = goToRegister,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}