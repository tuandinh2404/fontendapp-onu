package com.example.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray

@Composable
fun OpenningScreen(
    navController: NavHostController,
    goToRegister:() -> Unit ,
    goToLogin: () -> Unit

) {
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                Modifier
                    .size(80.dp)
                    .clickable{
                        goToLogin()
                    }
                    .background(LightGray, CircleShape),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "Login",
                )
            }
            Box(
                Modifier
                    .size(80.dp)
                    .clickable{
                        goToRegister()
                    }
                    .background(DarkGray, CircleShape),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "Register",
                )
            }

        }
    }
}