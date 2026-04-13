package com.example.moments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moments.component.item_bottom
import com.example.moments.component.item_header


data class Message(
    val isMe: Boolean,
    val time: String,
    val position: Int
)

@Composable
fun messenger_detail(
    mainController: NavHostController
) {
    val state = rememberLazyListState()
    val fakeMessages = listOf(
        Message(false, "10:34", 1),
        Message(true,"10:50", 2),
        Message(false,"11:34", 3),
        Message(true,"11:56",4),
        Message(false, "12:13",5),
        Message(true, "12:34",6),
        Message( false, "13:34", 7),
        Message(true, "13:56", 8),
        Message(false, "14:13",9),
        Message( true, "14:34", 10),
        Message(false, "15:34", 11),
        Message(true, "15:56",12),
    )
    val soccre = fakeMessages.sortedBy { it.position }
    Scaffold(
        topBar = {
            item_header(mainController)
        },
        bottomBar = {
            item_bottom()
        },
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .background(Color.Black)
        ) {
            LazyColumn(
                state = state,
                reverseLayout = true,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 70.dp,
                    bottom = 90.dp
                )
            ) {
                items(soccre.reversed()) { message ->
                    chat_bubble(message)
                }
            }

        }
    }
}

@Composable
fun chat_bubble(
    message: Message
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 30.dp),
        horizontalArrangement = if (message.isMe)
            Arrangement.End
        else
            Arrangement.Start
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .background(
                    if (message.isMe) Color(0xFF0084FF) else Color(0xFFE5E5EA),
                    RoundedCornerShape(9.dp)
                )
                .padding(6.dp)
        ) {


            Box(
                modifier = Modifier
                    .width(330.dp)
                    .aspectRatio(4f/5f)
            ) {
                Row(
                    Modifier
                        .align(if(message.isMe) Alignment.TopEnd else Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!message.isMe) {
                        Box(
                            Modifier
                                .padding(start = 4.dp, top = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(Color.Red, CircleShape)

                            )
                        }
                    } else {
                        Box(
                            Modifier
                                .padding(end = 4.dp, top = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(Color.Yellow, CircleShape)

                            )
                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = message.time,
                        fontSize = 10.sp,
                        fontWeight = FontWeight(600)
                    )
                }
            }
        }
    }
}
