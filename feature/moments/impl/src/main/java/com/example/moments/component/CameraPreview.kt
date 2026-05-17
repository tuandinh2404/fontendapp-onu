package com.example.moments.component

import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onTextureAvailable: (TextureView) -> Unit,
    onSurfaceDestroyed: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val textureViewRef = remember { mutableStateOf<TextureView?>(null) }
    val isCameraReleased = remember { mutableStateOf(false) }

    val currentOnTextureAvailable by rememberUpdatedState(onTextureAvailable)
    val currentOnSurfaceDestroyed by rememberUpdatedState(onSurfaceDestroyed)

// Wrap onSurfaceDestroyed
    val safeRelease = {
            if (!isCameraReleased.value) {
                isCameraReleased.value = true
                currentOnSurfaceDestroyed()
            }
        }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    textureViewRef.value
                        ?.takeIf { it.isAvailable }
                        ?.let(currentOnTextureAvailable)
                }
                Lifecycle.Event.ON_PAUSE -> safeRelease()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            safeRelease()
        }
    }

    AndroidView(
        factory = { ctx ->
            TextureView(ctx).apply {
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                surfaceTextureListener = object : TextureView.SurfaceTextureListener {

                    override fun onSurfaceTextureAvailable(
                        texture: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        isCameraReleased.value = false
                        textureViewRef.value = this@apply

                        currentOnTextureAvailable(this@apply)
                    }

                    override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
                        textureViewRef.value = null
                        safeRelease()
                        return true
                    }

                    override fun onSurfaceTextureSizeChanged(
                        s: SurfaceTexture,
                        w: Int,
                        h: Int
                    ) {}

                    override fun onSurfaceTextureUpdated(s: SurfaceTexture) {}

                }
            }
        },
        modifier = modifier

    )
}