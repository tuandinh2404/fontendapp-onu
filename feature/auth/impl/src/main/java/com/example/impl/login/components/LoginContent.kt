package com.example.impl.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray
import com.example.impl.login.LoginUiState
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    uiState: LoginUiState,
    textUsername: String,
    onTextUsernameChange: (String) -> Unit,
    clearError: () -> Unit,
    onUsernameNext: () -> Unit,
    textPassword: String,
    onTextPasswordChange: (String) -> Unit,
    onPasswordDone: () -> Unit,
    showPassword: Boolean,
    usernameFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester
) {

    var passwordVisible by remember { mutableStateOf(false) }

    val isError = uiState is LoginUiState.UserNotFound
    val isPasswordError = uiState is LoginUiState.Error


    Box(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                top = 20.dp
            )
    ) {
        Text(
            text = if (showPassword) "Nhập mật khẩu" else "Nhập tên đăng nhập của bạn",
            fontSize = 40.sp,
            color = LightGray,
            fontWeight = FontWeight.ExtraBold
        )
    }
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center

    ) {
        LoginTextField(
            valueText = textUsername,
            onChange = { input ->
                if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                    onTextUsernameChange(input)
                    clearError()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
            ,
            placeholder = {
                    Text(
                        text = "Tên đăng nhập",
                        fontSize = 43.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.ExtraBold
                    )

            },
            textStyle = TextStyle(
                fontSize = 43.sp,
                color = LightGray,
                fontWeight = FontWeight.ExtraBold,
            ),
            clearError = clearError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = { onUsernameNext() }
            ),
            focusRequester = usernameFocusRequester,
            isError = isError,
            enabled = !showPassword
        )
    }

    if(textUsername.isNotEmpty() && !isError && !showPassword) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 20.dp
                ),
        ) {
            Text(
                text = "Chỉ chứa chữ cái, số và dấu gạch dưới",
                fontSize = 15.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
    }
    if (isError) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 20.dp
                ),
        ) {
            Text(
                text = "Tài khoản người dùng hiện không tồn tại với tên đăng nhập này",
                fontSize = 15.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
            )

        }
    }
    if(showPassword && !isError) {
        InputPassword(
            textPassword = textPassword,
            onTextPasswordChange = onTextPasswordChange,
            onPasswordDone = onPasswordDone,
            onTogglePassword = { passwordVisible = !passwordVisible },
            passwordVisible = passwordVisible,
            clearError = clearError,
            showPassword = showPassword,
            passwordFocusRequester = passwordFocusRequester,
            isPasswordError = isPasswordError
        )
    }
    if(isPasswordError) {
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
                text = "Mật khẩu bạn đã nhập không chính xác!",
                fontSize = 15.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun InputPassword(
    textPassword: String,
    onTextPasswordChange: (String) -> Unit,
    onPasswordDone: () -> Unit,
    onTogglePassword: () -> Unit,
    passwordVisible: Boolean,
    clearError: () -> Unit,
    showPassword: Boolean,
    passwordFocusRequester: FocusRequester,
    isPasswordError: Boolean
) {
    AnimatedVisibility(
        visible = showPassword,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier =  Modifier
            .fillMaxWidth()
    ) {
        LoginTextField(
            valueText = textPassword,
            onChange = {
                onTextPasswordChange(it)
                clearError()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
            ,
            placeholder = {
                Text(
                    text = "Mật khẩu",
                    fontSize = 43.sp,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 43.sp,
                color = LightGray,
                fontWeight = FontWeight.ExtraBold,
            ),
            clearError = clearError,
            trailingIcon = {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) OnuIcons.EyeShow else OnuIcons.EyeHide
                    ),
                    contentDescription = "Password visibility",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onTogglePassword()
                        }
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onPasswordDone() }
            ),
            focusRequester = passwordFocusRequester,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isPasswordError
        )

    }

}