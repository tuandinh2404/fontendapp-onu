package com.example.moments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.moments.CameraViewModel

@Composable
fun moments_preview(
    viewModel: CameraViewModel = hiltViewModel(),
    onFetchLocation: () -> Unit
) {
    val location by viewModel.location.collectAsState()

    LaunchedEffect(Unit) {
        onFetchLocation()
    }
    Column(
        Modifier
            .fillMaxSize()
            .zIndex(10f)
            .pointerInput(Unit) {}
            .background(DarkGray)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
           verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Send to...",
                color = LightGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .background(LightGray)
                .padding(10.dp)

        ) {
            viewModel.capturedBitmap?.let { bitmap ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(viewModel.capturedBitmap)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .aspectRatio(3f/4f)
                    ,
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                Modifier
                    .align(Alignment.Center)
            ) {
                location?.let {
                    Text(
                        text = listOf(
                            it.district,
                            it.city
                        )
                            .filterNotNull()
                            .distinct()
                            .joinToString(", "),
                        color = LightGray,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center

        ) {
            Box(
                Modifier
                    .padding(start = 6.dp)
            ) {
                Box(
                    Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
        }
    }
}