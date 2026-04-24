package com.example.impl.openning.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuImages
import com.example.designsystem.theme.CoolWhite
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.LightPeachBeige
import com.example.designsystem.theme.NunitoFontFamily

@Composable
fun OpenningContent(
    selectedDay: Int,
    selectedMonth: Int,
    listStateDay: LazyListState,
    listStateMonth: LazyListState,
    onChangeDay: (Int) -> Unit,
    onChangeMonth: (Int) -> Unit,
    hasContended: Boolean
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
    ) {
        AnimatedVisibility(
            visible = hasContended,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .padding(top = 10.dp),
        ) {
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.8f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Gray),
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Bắt đầu với một số thông tin nhé!",
                    fontSize = 33.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color = CoolWhite,
                    textAlign = TextAlign.Center,
                    lineHeight = TextUnit.Unspecified
                )

            }
        }

    }

//    Box(
//        Modifier
//            .fillMaxWidth(),
//        contentAlignment = Alignment.Center
//    ) {
//        Box(
//            Modifier
//                .padding(bottom = 4.dp)
//        ) {
//            Box(
//                Modifier
//                    .fillMaxWidth(0.75f)
//                    .height(40.dp)
//                    .align(Alignment.Center)
//                    .background(
//                        color = Color.White.copy(alpha = 0.08f),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//            )
//        }
//        Box(
//            Modifier
//                .align(Alignment.CenterEnd)
//                .padding(end = 40.dp, bottom = 20.dp)
//        ) {
//            Image(
//                painter = painterResource(OnuImages.ArrowLeft),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(40.dp)
//            )
//        }
//        Row(
//        ) {
//            WheelPicker(
//                items = (1..31).toList(),
//                listState = listStateDay,
//
//                prefix = "",
//                width = 80,
//                key = "days"
//            ) { days ->
//                onChangeDay(days)
//            }
//            WheelPicker(
//                items = (1..12).toList(),
//                listState = listStateMonth,
//                prefix = "tháng ",
//                width = 100,
//                key = "months"
//            ) { months ->
//                onChangeMonth(months)
//            }
//        }
//    }
}