package com.example.onu.features.friends.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onu.R
import com.example.onu.core.ui.components.customOverScroll
import com.example.onu.features.moments.ui.component.moments_topbar
import com.example.onu.ui.theme.DarkGray
import com.example.onu.ui.theme.LightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun friend_screen(

) {
    val focus = LocalFocusManager.current
    val state = rememberLazyListState()
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var offsetY by remember { mutableFloatStateOf(0f) }
    var hasNavigated by remember { mutableStateOf(false) }
    val maxDrag = 300f
    val progress = (offsetY / maxDrag).coerceIn(0f, 1f)
    val haptic = LocalHapticFeedback.current


    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focus.clearFocus() }
            }
            .background(DarkGray)
    ) {
        FriendsListContent(
            state = state,
            text = text,
            isFocused = isFocused,
            onTextChange = { text = it },
            onFocusChange = { isFocused = it }
        )
        moments_topbar(
            modifier = Modifier
                .align(Alignment.TopCenter),
            progress = { progress },
            onOpen = {
                offsetY = 0f
                hasNavigated = false
            }
        )


    }
}


@Composable
fun FriendsListContent(
    state: LazyListState,
    text: String,
    isFocused: Boolean,
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit
) {
    var overScrollOffset by remember { mutableFloatStateOf(0f) }
    LazyColumn(
        Modifier
            .graphicsLayer {
                translationY = overScrollOffset
            }
            .fillMaxSize()
            .customOverScroll(
                listState = state,
                onNewOverScrollAmount = { value ->
                    overScrollOffset = value
                }
            )
        ,
        state = state,
        contentPadding = PaddingValues(
            top = 110.dp,
            bottom = 70.dp
        )
    ) {
        FriendsSection()
        ShareLinkSection()
    }
}
fun LazyListScope.FriendsSection(
) {
    item {
        Box(
            Modifier
                .fillMaxWidth(),
        ) {
            Box(
                Modifier
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Bạn bè",
                    fontSize = 20.sp,
                    color = LightGray
                )
            }
        }
    }
    items(10, key = { "friend_$it" }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .size(60.dp)
                        .background(LightGray, CircleShape)
                )
                Text(
                    text = "Nguyễn Văn A",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            }
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(30.dp)
            )

        }

    }
}


fun LazyListScope.ShareLinkSection(

) {
    item {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Chia sẻ liên kết Onu của bạn",
                    fontSize = 20.sp,
                    color = LightGray

                )
            }
        }
    }
}