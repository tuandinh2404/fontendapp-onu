package com.example.moments.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.LightGray
import com.example.moments.CameraViewModel
import com.example.moments.MomentsViewModel

@Composable
fun MomentsPreviewScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onFetchLocation: () -> Unit,
    onClosed: () -> Unit,
) {
    val location by viewModel.location.collectAsState()

    LaunchedEffect(Unit) {
        onFetchLocation()
    }

    Box(
        Modifier
            .fillMaxSize()
            .zIndex(10f)
            .pointerInput(Unit) {}
            .background(DarkGray)
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                Box(
                    Modifier
                        .size(40.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onClosed()
                        }
                        .background(LightGray, CircleShape)
                )
                Text(
                    text = "Send to...",
                    color = LightGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Box(
                    Modifier
                        .size(40.dp)
                        .background(LightGray, CircleShape)
                )
            }

            Box(
                Modifier
                    .fillMaxWidth(0.9f),
                contentAlignment = Alignment.Center

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
                            .aspectRatio(3f / 4f),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .background(LightGray, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Send",
                        color = DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Box(
                    Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                )
            }
        }
    }
}