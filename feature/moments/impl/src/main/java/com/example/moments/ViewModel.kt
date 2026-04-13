package com.example.moments

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CameraViewModel : ViewModel() {

    var isOpenedPreview  by mutableStateOf(false)
        private set

    var isCaptured by mutableStateOf(false)
        private set

    var capturedBitmap by mutableStateOf<Bitmap?>(null)
        private set
    var capturedPressScale by mutableStateOf(1f)
        private set

    fun onCapture(pressScale:Float) {
        isCaptured = true
        capturedPressScale = pressScale
        isOpenedPreview = true
    }


    fun onPhotoTaken(bitmap: Bitmap) {
        capturedBitmap = bitmap
        viewModelScope.launch {
            delay(300)
            isCaptured = false
        }
    }

    fun closePreview() {
        capturedBitmap = null
        isOpenedPreview = false
        isCaptured = false
        capturedPressScale = 1f
    }
}