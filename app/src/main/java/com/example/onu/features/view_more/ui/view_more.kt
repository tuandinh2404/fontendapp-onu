package com.example.onu.features.view_more.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onu.features.moments.ui.PagerViewModel
import com.example.onu.core.ui.components.SuperEllipseShape

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun view_more(
    mainController: NavHostController,
    builderController: NavHostController,
    backScreen: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: PagerViewModel = viewModel()
) {
    val images = viewModel.items
    Scaffold(
        containerColor = Color.Black
    ) { paddingValues ->


        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImageGridItem(
    imageUrl: Int,
    imageId: Int,
    onExitGrid: () -> Unit
) {
    Box(
        Modifier
            .padding(5.dp)
            .clickable {
                onExitGrid()
            }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .graphicsLayer {
                    shape = SuperEllipseShape(n = 2.5f)
                    clip = true
                }

                .background(Color.DarkGray)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image $imageId",
                modifier = Modifier
                    .fillMaxSize()
//                        .sharedElement(
//                            state = rememberSharedContentState("friend_photo$imageId").also {
//                                println("GridItem key: image_$imageId")  // Log
//                            },
//                            animatedVisibilityScope,
//                            clipInOverlayDuringTransition = OverlayClip(
//                                SuperEllipseShape(n = 2.5f),
//                            )
//                        )
                ,
                contentScale = ContentScale.Crop
            )
        }
    }
}