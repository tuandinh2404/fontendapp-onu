package com.example.onu.router

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.impl.LoginScreen
import com.example.impl.RegisterScreen


sealed class Auth_Router(
    val route: String
) {
    object OpenningScreen: Auth_Router("openning")
    object LoginScreen: Auth_Router("login")
    object RegisterScreen: Auth_Router("register")
    object NavigationBuilder: Auth_Router("navigation")
}

@Composable
fun AuthRouter(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Auth_Router.NavigationBuilder.route
    ) {
//        composable(Auth_Router.OpenningScreen.route) {
//            openning_screen(navController)
//        }
        composable(Auth_Router.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Auth_Router.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(Auth_Router.NavigationBuilder.route) {
            NavigationBuilder(navController)
        }
    }
}


