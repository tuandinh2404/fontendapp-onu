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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.impl.register.components.CustomStepProgress
import com.example.impl.register.components.RegisterContent
import com.example.impl.register.components.StepTextField
import com.example.impl.register.components.TopBar



@Composable
fun RegisterScreen(
    navController: NavHostController,
    goBack: () -> Unit,
    goToLogin:() -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
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
                goBack = {
                    if(formState.step == SignupStep.USERNAME) {
                        goBack()
                    } else {
                        viewModel.goBack()
                    }
                },
                goLogin = goToLogin
            )
            CustomStepProgress(
                currentStep = formState.step.getStepNumber(),
                totalSteps = 5,
                modifier = Modifier.padding(top =  10.dp)
            )
            RegisterContent(
                formState = formState,
                uiState = uiState,
                onCheckUsername = { viewModel.checkUsername(it) },
                onNextStep = { viewModel.nextStep(it) },
                onSignUp = { password, fullName, uid ->
                    viewModel.signUp(password, fullName, uid) },
                onClearError = { viewModel.clearError() }
            )
        }

    }
}



