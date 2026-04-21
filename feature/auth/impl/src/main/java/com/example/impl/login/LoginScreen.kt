package com.example.impl.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.impl.login.components.LoginButton
import com.example.impl.login.components.LoginTextField
import com.example.impl.login.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    goBack: () -> Unit,
    goToMain:() -> Unit ,
    goToRegister:() -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var textUsername by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    var showPassword by remember { mutableStateOf(false) }
    var textPassword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val goMain = {
        scope.launch {
            focusManager.clearFocus()
            keyboardController?.hide()
            goToMain()
        }
    }


    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> { goMain() }
            is LoginUiState.UserValid -> showPassword = true
            else -> Unit
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray)
            .imePadding()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            TopBar(
                goBack = {
                    scope.launch {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        goBack()
                    }
                },
                goRegister = goToRegister
            )
            LoginContent(
                uiState = uiState,
                textUsername = textUsername,
                onTextUsernameChange = { username ->
                    textUsername = username
                },
                clearError = { viewModel.clearError() },
                onUsernameNext = {
                    if (textUsername.isNotEmpty()) {
                        viewModel.checkUsername(textUsername)
                    }
                },
                textPassword = textPassword,
                onTextPasswordChange = { textPassword = it },
                onPasswordDone = {
                    if (textPassword.isNotEmpty()) {
                        viewModel.login(textUsername, textPassword)
                    }
                },
                showPassword = showPassword,
            )

        }
        LoginButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = if(showPassword) textPassword else textUsername,
            isLoading = uiState is LoginUiState.CheckingUsername || uiState is LoginUiState.LoggingIn,
            onClick = {
                if(!showPassword) {
                    viewModel.checkUsername(textUsername)
                } else {
                    viewModel.login(textUsername, textPassword)
                }
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    uiState: LoginUiState,
    textUsername: String,
    onTextUsernameChange: (String) -> Unit,
    clearError: () -> Unit,
    onUsernameNext: () -> Unit,
    textPassword: String,
    onTextPasswordChange: (String) -> Unit,
    onPasswordDone: () -> Unit,
    showPassword: Boolean,
) {
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    var passwordVisible by remember { mutableStateOf(false) }

    val isError = uiState is LoginUiState.UserNotFound
    val isPasswordError = uiState is LoginUiState.Error


    LaunchedEffect(Unit) {
        usernameFocusRequester.requestFocus()
    }
    LaunchedEffect(showPassword) {
        if (showPassword) {
            passwordFocusRequester.requestFocus()
        }
    }

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
            .fillMaxWidth()

    ) {
        LoginTextField(
            valueText = textUsername,
            onChange = { input ->
                if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                    onTextUsernameChange(input)
                    clearError()
                }
            },
            placeholder = "Tên đăng nhập",
            clearError = clearError,
            leadingIcon = {
                Icon(
                    painter = painterResource(OnuIcons.User),
                    contentDescription = "Username",
                    tint = if(isError) Color.Red else LightGray,
                    modifier = Modifier
                        .size(25.dp)
                )
            },
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
                )
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
//            Box(
//                Modifier
//                    .size(15.dp)
//                    .background(
//                        color = Color.Red,
//                        shape = CircleShape
//                    ),
//                contentAlignment = Alignment.Center
//
//            ) {
//                Icon(
//                    painter = painterResource(OnuIcons.Close),
//                    contentDescription = "Error",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(15.dp)
//                )
//            }
            Text(
                text = "Tài khoản người dùng hiện không tồn tại với tên đăng nhập này",
                fontSize = 15.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
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
//            Box(
//                Modifier
//                    .size(15.dp)
//                    .background(
//                        color = Color.Red,
//                        shape = CircleShape
//                    ),
//                contentAlignment = Alignment.Center
//
//            ) {
//                Icon(
//                    painter = painterResource(OnuIcons.Close),
//                    contentDescription = "Error",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(15.dp)
//                )
//            }
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
fun InputPassword(
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
            placeholder = "Mật khẩu",
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
            isError = isPasswordError
        )

    }

}