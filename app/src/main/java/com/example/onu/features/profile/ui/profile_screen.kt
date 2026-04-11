package com.example.onu.features.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onu.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profile_screen(
    mainController: NavHostController,
    state: LazyListState,
    onClickShowSetting: () -> Unit

) {


    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.88f)
    ) {
        Scaffold(
            Modifier
                .fillMaxSize(),
            topBar = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Red)
                ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.setting),
                            contentDescription = "Show setting",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    onClickShowSetting()
                                }
                        )
                    }

                }
            }
        ) { paddingValues ->



            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 100.dp)

            ) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(Color.Gray, RoundedCornerShape(30.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "THem nhieu ban be",
                                fontSize = 20.sp,
                                fontWeight = FontWeight(800),
                                color = Color.Black
                            )
                        }
                    }
                }
                items(6,key = {it}) { index ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .background(Color.Yellow, RoundedCornerShape(15.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "item ${index + 1}",
                                    color = Color.Black

                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.Black, RoundedCornerShape(30.dp))
                        )
                    }
                }
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.Red, RoundedCornerShape(30.dp))
                        )
                    }
                }
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.Blue, RoundedCornerShape(30.dp))
                        )
                    }
                }
            }

        }


    }

}