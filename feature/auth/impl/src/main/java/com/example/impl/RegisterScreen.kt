package com.example.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.impl.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    goBack: () -> Unit,
    goToLogin:() -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var textUsername by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray)
            .imePadding()
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            TopBar(
                goBack = goBack,
                goLogin = goToLogin
            )
            RegisterContent(
                textUsername = textUsername,
                onChangeUsername = { username ->
                    textUsername = username
                }
            )
        }

    }
}

@Composable
private fun TopBar(
    goBack:() -> Unit,
    goLogin:() -> Unit
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
//                        goLogin()
//                    }
//            )
//        }
    }
}

@Composable
fun RegisterContent(
    textUsername: String,
    onChangeUsername: (String) -> Unit
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
            text = "Nghĩ về tên đăng nhập của bạn!",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = LightGray
        )

    }
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = textUsername,
            onValueChange = { input ->
                if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                    onChangeUsername(input)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
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
                cursorColor = Color.Blue.copy(alpha = 0.8f),
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            )
        )
    }

}