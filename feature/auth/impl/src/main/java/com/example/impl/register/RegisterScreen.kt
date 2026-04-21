package com.example.impl.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.impl.register.components.StepTextField
import com.example.impl.register.components.TopBar


enum class SignupStep {
    USERNAME,
    PASSWORD,
    FULLNAME,
    UID,
}
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
            text = "Nghĩ về tên đăng nhập của bạn?",
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
        StepTextField(
            value = textUsername,
            onChange = { input ->
                if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                    onChangeUsername(input)
                }
            },
            placeholder = "Tên đăng nhập",

        )
    }
    if(textUsername.isEmpty()) {
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Box(
                    Modifier
                        .weight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "hoặc",
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )
                }
                Box(
                    Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }
    }

}

