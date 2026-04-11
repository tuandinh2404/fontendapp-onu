package com.example.onu.features.camera.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun camera_screen(
    mainController: NavHostController,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onDownClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Blue),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(50.dp)
                            .background(Color.Red, CircleShape)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onLeftClick()
                            }
                    )
                    Box(
                        Modifier
                            .size(50.dp)
                            .background(Color.Green, CircleShape)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onRightClick()
                            }
                    )

                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                ,
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier


                ) { page ->
                    when (page) {
                        0 -> itemsTest(colorBackground = Color.Red)
                        1 -> itemsTest(colorBackground = Color.Green)
                        2 -> itemsTest(colorBackground = Color.Black)
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chuyentab",
                    modifier = Modifier
                        .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                            onDownClick()
                    }
                )

            }
        }
}

@Composable
fun itemsTest(
    colorBackground: Color
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.6f)
                .background(colorBackground, RoundedCornerShape(30.dp))
        )
    }
}
