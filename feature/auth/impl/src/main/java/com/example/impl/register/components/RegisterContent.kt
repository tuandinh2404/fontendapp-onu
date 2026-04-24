package com.example.impl.register.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.LightGray
import com.example.impl.register.RegisterFormState
import com.example.impl.register.RegisterUiState
import com.example.impl.register.SignupStep
import com.example.impl.register.components.steps.FullNameStep
import com.example.impl.register.components.steps.PasswordStep
import com.example.impl.register.components.steps.UidStep
import com.example.impl.register.components.steps.UsernameStep

@Composable
fun RegisterContent(
    formState: RegisterFormState,
    uiState: RegisterUiState,
    onCheckUsername: (String) -> Unit,
    onNextStep: (SignupStep) -> Unit,
    onSignUp: (password: String, fullName: String, uid: String) -> Unit,
    onClearError: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }

    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var fullNameText by remember { mutableStateOf("") }
    var uidText by remember { mutableStateOf("") }

    val isLoading = uiState is RegisterUiState.Loading
    val isError = uiState is RegisterUiState.Error
    val errorMessage = (uiState as? RegisterUiState.Error)?.message

    val buttonEnabled = when (formState.step) {
        SignupStep.USERNAME -> usernameText.isNotBlank() && usernameText.length >= 6 && !isLoading
        SignupStep.PASSWORD -> passwordText.isNotBlank()
        SignupStep.FULLNAME -> fullNameText.isNotBlank()
        SignupStep.UID -> uidText.isNotBlank() && uidText.length >= 6 && !isLoading
    }

    val onButtonClick: () -> Unit = {
        when (formState.step) {
            SignupStep.USERNAME -> onCheckUsername(usernameText)
            SignupStep.PASSWORD -> {
                password = passwordText
                onNextStep(SignupStep.FULLNAME)
            }
            SignupStep.FULLNAME -> {
                fullName = fullNameText
                onNextStep(SignupStep.UID)
            }
            SignupStep.UID -> onSignUp(password, fullName, uidText)
        }
    }

    Box(
        Modifier.fillMaxSize().imePadding()
    ) {
        AnimatedContent(
            targetState = formState.step,
            label = "stepAnimation",
            modifier = Modifier.padding(top = 20.dp)
        ) { step ->
            when (step) {
                SignupStep.USERNAME -> UsernameStep(
                    textUsername = usernameText,
                    onChangeUsername = { usernameText = it },
                    isLoading = isLoading,
                    isError = isError,
                    errorMessage = errorMessage,
                    onClearError = onClearError,
                    onNext = {
                        if (buttonEnabled) onButtonClick()
                    }
                )

                SignupStep.PASSWORD -> PasswordStep(
                    textPassword = passwordText,
                    onChangePassword = { passwordText = it },
                )

                SignupStep.FULLNAME -> FullNameStep(
                    textFullName = fullNameText,
                    onChangeFullName = { fullNameText = it }
                )

                SignupStep.UID -> UidStep(
                    textUid = uidText,
                    onChangeUid = { uidText = it },

                )
            }
        }
        NextButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            isLoading = isLoading,
            enabled = buttonEnabled,
            onClick = onButtonClick
        )
    }


}