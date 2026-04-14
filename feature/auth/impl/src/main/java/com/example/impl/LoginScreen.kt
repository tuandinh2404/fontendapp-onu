package com.example.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray

@Composable
fun LoginScreen(
    navController: NavHostController,
    goBack: () -> Unit,
    goToMain:() -> Unit ,
    goToRegister:() -> Unit
) {
    var text by remember { mutableStateOf("") }
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            TopBar(
                goBack = goBack,
                goRegister = goToRegister
            )
            LoginContent(
                text = text,
                onTextChange = { text = it }
            )
        }
        LoginButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = text
        )
    }

}

@Composable
private fun TopBar(
    goBack:() -> Unit,
    goRegister:() -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            Modifier
                .padding(
                    start = 20.dp
                )
        ) {
            Icon(
                painter = painterResource(OnuIcons.ArrowBack),
                contentDescription = "Back",
                tint = LightGray,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        goBack()
                    }
            )
        }
//        Box(
//            Modifier
//                .padding(
//                    end = 20.dp
//                )
//        ) {
//            Text(
//                text = "Đã có tài khoản!",
//                fontSize = 20.sp,
//                color = LightGray,
//                modifier = Modifier
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() }
//                    ) {
//                        goRegister()
//                    }
//            )
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    text: String,
    onTextChange: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                top = 20.dp
            )
    ) {
        Text(
            text = "Nhập tên đăng nhập của bạn",
            fontSize = 30.sp,
            color = LightGray,
            fontWeight = FontWeight.ExtraBold
        )
    }
    Box(
        Modifier
            .fillMaxWidth()

    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Tên đăng nhập",
                    fontSize = 25.sp,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 25.sp,
                color = LightGray,
                fontWeight = FontWeight.ExtraBold
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue.copy(alpha = 0.8f)
            )
        )

    }
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier
            .fillMaxWidth()
            .imePadding()
            .padding(
                bottom = 20.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.9f)
                .height(60.dp)
                .clip(CircleShape)
                .background(if(text.isNotEmpty()) LightGray else Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tiếp tục",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if(text.isNotEmpty()) DarkGray else LightGray.copy(alpha = 0.3f),
            )
        }
    }

}