package com.example.impl.register.components.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.LightGray
import com.example.impl.register.components.StepTextField

@Composable
fun UsernameStep(
    textUsername: String,
    onChangeUsername: (String) -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String?,
    onClearError: () -> Unit,
    onNext: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp
                )
        ) {
            Text(
                text = "Nghĩ về tên đăng nhập của bạn?",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LightGray
            )

        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)

        ) {
            StepTextField(
                value = textUsername,
                onChange = { input ->
                    if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                        onChangeUsername(input)
                        onClearError()
                    }
                },
                placeholder = "Tên đăng nhập",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onDone = { onNext(textUsername) }),
                enabled = !isLoading
            )
        }
        if (isError) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp,
                        start = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tài khoản người dùng hiện đã tồn tại với tên đăng nhập này",
                    fontSize = 15.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}