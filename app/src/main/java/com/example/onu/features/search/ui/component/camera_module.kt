package com.example.onu.features.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onu.R
import com.example.onu.core.ui.components.SuperEllipseShape

@Composable
fun camera_module(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit,
    hasCameraPermission: Boolean,
    openAppSettings: () -> Unit
) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.97f)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        shape = SuperEllipseShape(n = 2.5f)
                        clip = true
                    }
            ) {
                //Camera
                if (!hasCameraPermission) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(0.4f)
                                .height(40.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }

                                ) {
                                    openAppSettings()
                                }
                                .background(Color.Gray.copy(alpha = 0.8f), RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cho phép",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(800)
                            )
                        }

                    }
                } else {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
            }
            //Mở tắt flash
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 10.dp, start = 15.dp)
            ) {
                Icon(
                    painter = if (isOn) painterResource(R.drawable.flash) else painterResource(
                        R.drawable.flash_no
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }

                        ) {
                            onToggle(!isOn)
                        },
                    tint = Color.White
                )

            }
            //Phần zoom Camera
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 15.dp)
            ) {
                zoom_camera()
            }
        }

    }
}