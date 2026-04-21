package com.example.onu.router

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.DarkGray
import com.example.impl.login.LoginScreen
import com.example.impl.openning.OpenningScreen
import com.example.impl.register.RegisterScreen


sealed class Auth_Router(
    val route: String
) {
    object OpenningScreen: Auth_Router("openning")
    object LoginScreen: Auth_Router("login")
    object RegisterScreen: Auth_Router("register")
    object NavigationBuilder: Auth_Router("navigation")
    object OnboardingScreen: Auth_Router("onboarding")
}

@Composable
fun AuthRouter(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Auth_Router.OpenningScreen.route,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        composable(Auth_Router.OpenningScreen.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }
        ) {
            OpenningScreen(
                navController = navController,
                goToRegister = {
                    navController.navigate(Auth_Router.RegisterScreen.route) {
                        launchSingleTop = true

                    }
                },
                goToLogin = {
                    navController.navigate(Auth_Router.LoginScreen.route) {
                        launchSingleTop = true

                    }
                }
            )
        }
        composable(Auth_Router.LoginScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)

                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)

                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)

                )
            }
        ) {
            LoginScreen(
                navController = navController,
                goBack = { navController.navigateUp() },
                goToMain = { navController.navigate(Auth_Router.NavigationBuilder.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                } },
                goToRegister = {
                    navController.navigate(Auth_Router.RegisterScreen.route) {
                        launchSingleTop = true

                    }
                }
            )
        }
        composable(Auth_Router.RegisterScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)

                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)

                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)

                )
            }
        ) {
            RegisterScreen(
                navController = navController,
                goBack = { navController.navigateUp() },
                goToLogin = {
                    navController.navigate(Auth_Router.LoginScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Auth_Router.OnboardingScreen.route) {
            OnboardingScreen(
                navController = navController,
                goToLogin = {
                    navController.navigate(Auth_Router.LoginScreen.route) {
                        launchSingleTop = true
                    }
                },
                goToMain = {
                    navController.navigate(Auth_Router.NavigationBuilder.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Auth_Router.NavigationBuilder.route) {
            NavigationBuilder(navController)
        }
    }
}

@Composable
fun NavGraphBuilder.OnboardingScreen(
    navController: NavHostController,
    goToLogin: () -> Unit,
    goToMain: () -> Unit
) {

}


