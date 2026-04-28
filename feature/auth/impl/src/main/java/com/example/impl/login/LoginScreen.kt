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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.impl.login.components.LoginButton
import com.example.impl.login.components.LoginContent
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

    val enabledButton = if (!showPassword) {
        textUsername.isNotEmpty() && textUsername.length >= 6
    } else {
        textPassword.isNotEmpty()
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
            text = enabledButton,
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