package com.example.onu.router

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onu.features.create_moment.ui.create_moment
import com.example.onu.features.moments.ui.messenger_detail
import com.example.onu.router.MainRouter.Main


@OptIn(ExperimentalAnimationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationBuilder(
    navController: NavHostController
) {
    val builderController = rememberNavController()


    SharedTransitionLayout(
    ) {
        NavHost(
            navController = builderController,
            startDestination = NavigationBuilder_Router.main_router.route,
            modifier = Modifier
                .background(Color.Black)
        ) {
            composable(NavigationBuilder_Router.main_router.route) {
                Main(
                    builderController,
                )
            }
            composable(
                NavigationBuilder_Router.messenger_detail.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        tween(300)

                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        tween(300)

                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        tween(300)

                    )
                },
            ) {
                messenger_detail(builderController)
            }
            composable(
                NavigationBuilder_Router.create_moment.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                    )
                },
            ) {
                create_moment(
                    builderController = builderController,
                    backScreen = { builderController.navigateUp() }
                )
            }


        }
    }
}

sealed class NavigationBuilder_Router(
    val route: String
) {
    object main_router: NavigationBuilder_Router("main_router")
    object messenger_detail: NavigationBuilder_Router("messenger_detail")
    object create_moment: NavigationBuilder_Router("create_moment")
}