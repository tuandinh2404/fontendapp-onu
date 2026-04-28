package com.example.impl.openning.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.CoolWhite
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.NunitoFontFamily
import kotlin.math.abs
import kotlin.math.cos

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
    val repeatCount = 15
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
        contentPadding = PaddingValues(vertical = itemHeight * 2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(totalItems, key = { index -> "${key}_${index}" }) { index ->
            val distance = abs(index - centerIndex)
            val realItem = items[index % items.size]
            val isCenter = distance == 0

            val colorText = if (isCenter) CoolWhite else Color.Gray

            Box(
                Modifier
                    .height(itemHeight)
                    .width(width.dp)
//                    .graphicsLayer {
//                        cameraDistance = 6f * this.density
//                    }
                ,
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "$prefix${realItem}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(5.dp),
                    color = colorText,
                    maxLines = 1,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = NunitoFontFamily
                )
            }
        }

    }
}