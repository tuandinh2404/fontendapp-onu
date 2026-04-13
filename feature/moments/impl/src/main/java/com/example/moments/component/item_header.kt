package com.example.moments.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.designsystem.icon.OnuIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun item_header(
    mainController: NavHostController
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Black)
    ) {
        Row(
            Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .padding(top = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                ) {
                    Icon(
                        painter = painterResource(OnuIcons.ArrowBack),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                mainController.navigateUp()
                            },
                        tint = Color.White
                    )
                }
                Box(
                    Modifier
                        .size(30.dp)
                        .background(Color.Red, CircleShape)
                )
                Box(

                ) {
                    Text(
                        text = "Nhom ba con cuu",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White
                    )
                }
            }
            Box(
                Modifier
                    .padding(top = 25.dp, end = 10.dp),
            ) {
                Box(
                    Modifier
                        .size(30.dp)
                        .background(Color.Blue, CircleShape),
                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        painter = painterResource(OnuIcons.Add),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }

    }

}