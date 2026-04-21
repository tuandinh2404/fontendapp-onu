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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.impl.openning.OpenningViewModel
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
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray),
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
                hasInteracted = viewModel.hasInteracted
            )
        }
        AnimatedVisibility(
            visible = viewModel.hasInteracted,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .scale(scale)
                    .fillMaxWidth(0.5f)
                    .height(60.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        goToRegister()
                    }
                    .background(LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
//                Text(
//                    text = "Tiếp nào\uD83D\uDE0E",
//                    color = LightGray,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//
//                )
                Icon(
                    painter = painterResource(OnuIcons.ArrowRight),
                    contentDescription = null,
                    tint = DarkGray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun TopBar(
    onGoLogin:() -> Unit

) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            Modifier
                .padding(
                    end = 20.dp
                )
        ) {
            Text(
                text = "Đăng nhập?",
                fontSize = 20.sp,
                color = LightGray,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onGoLogin()
                    },
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun OpenningContent(
    selectedDay: Int,
    selectedMonth: Int,
    listStateDay: LazyListState,
    listStateMonth: LazyListState,
    onChangeDay: (Int) -> Unit,
    onChangeMonth: (Int) -> Unit,
    hasInteracted: Boolean
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "moyaa.",
            color = LightGray,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )

    }

    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .padding(bottom = 4.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.75f)
                    .height(40.dp)
                    .align(Alignment.Center)
                    .background(
                        color = Color.White.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
        Row(

        ) {
            WheelPicker(
                items = (1..31).toList(),
                listState = listStateDay,

                prefix = "",
                width = 80,
                key = "days"
            ) { days ->
                onChangeDay(days)
            }
            WheelPicker(
                items = (1..12).toList(),
                listState = listStateMonth,
                prefix = "tháng ",
                width = 100,
                key = "months"
            ) { months ->
                onChangeMonth(months)
            }
        }
    }
    AnimatedVisibility(
        visible = hasInteracted,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ngày $selectedDay tháng $selectedMonth ✨",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun WheelPicker(
    items: List<Int>,
    listState: LazyListState,
    prefix: String = "",
    width: Int,
    key: String,
    onSelected: (Int) -> Unit
) {
    val flingBehavior = rememberSnapFlingBehavior( lazyListState = listState )
    val itemHeight = 40.dp
    val visibleItems = 5
    val pickerHeight = itemHeight * visibleItems
    val repeatCount = 50
    val totalItems = items.size * repeatCount

    val centerIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
            layoutInfo.visibleItemsInfo
                .minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }
                ?.index ?: listState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    val realItem = items[centerIndex % items.size]
                    onSelected(realItem)
                }
            }
    }
        LazyColumn(
            modifier = Modifier
                .height(pickerHeight),
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(vertical = itemHeight * 2), // padding 2 item trên/dưới
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(totalItems, key = { index -> "${key}_${index}" }) { index ->
                val distance = abs(index - centerIndex)
                val realItem = items[index % items.size]

                val rotation = distance * 20f
                // Item càng xa tâm càng bị thu nhỏ theo trục Y (hiệu ứng cong)
                val scaleY = cos(Math.toRadians(rotation.toDouble())).toFloat()
                    .coerceAtLeast(0.1f)
                // Alpha mờ dần ra ngoài
                val alpha = scaleY.coerceIn(0.1f, 1f)

                val colorText by animateColorAsState(
                    targetValue = when (distance) {
                        0 -> LightGray
                        else -> Color.Gray
                    },
                    animationSpec = tween(150),
                    label = "colorText"
                )
                Box(
                    Modifier
                        .height(itemHeight)
                        .width(width.dp)
                        .graphicsLayer {
                            cameraDistance = 6f * this.density
                        }

                ) {
                    Text(
                        text = "$prefix${realItem}",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(5.dp),
                        color = colorText,
                        maxLines = 1,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

        }
}