package com.example.moments.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.Executors

class camera_controller(
    private val context: Context
) {
    val controller = LifecycleCameraController(context).apply {
        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        setEnabledUseCases(
            CameraController.IMAGE_CAPTURE or
                    CameraController.IMAGE_ANALYSIS
        )
        imageCaptureMode  = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
    }

    private var lastFrame: Bitmap? = null
    private var isFrozen = false
    private val _isFrontCamera = MutableStateFlow(true)
    val isFrontCamera = _isFrontCamera.asStateFlow()
    private val analysisExecutor = Executors.newSingleThreadExecutor()

    fun startAnalisis() {
        controller.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context)
        ) { imageProxy ->
            val bitmap = imageProxy.toBitmap()
            val matrix = Matrix().apply {
                postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())

                if(_isFrontCamera.value) {
                    postScale(-1f, 1f)
                }
            }
            val correctedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.width, bitmap.height,
                matrix, true
            )
            imageProxy.close()

            if(!isFrozen) {
                lastFrame = correctedBitmap
            }

        }
    }

    fun capture(
//        onPreview: (Bitmap?) -> Unit,
        onFinal: (Bitmap?) -> Unit
    ) {
//        onPreview(lastFrame)

        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val bitmap = image.toBitmap()
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                        if(_isFrontCamera.value) {
                            postScale(-1f, 1f)
                        }

                    }
                    val result = Bitmap.createBitmap(
                        bitmap,
                        0, 0,
                        bitmap.width,
                        bitmap.height,
                        matrix,
                        true
                    )

                    image.close()

                    onFinal(result)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    onFinal(null)
                }
            }
        )
    }

    fun reset() {
        isFrozen = false
        lastFrame = null
    }

    fun flipCamera() {
        _isFrontCamera.value = !_isFrontCamera.value
        controller.cameraSelector =
            if (_isFrontCamera.value)
                CameraSelector.DEFAULT_FRONT_CAMERA
            else
                CameraSelector.DEFAULT_BACK_CAMERA
    }

    fun toggleFlash() {
        controller.imageCaptureFlashMode =
            if (controller.imageCaptureFlashMode == ImageCapture.FLASH_MODE_OFF)
                ImageCapture.FLASH_MODE_ON
            else ImageCapture.FLASH_MODE_OFF
    }
    fun pauseAnalysis() {
        controller.clearImageAnalysisAnalyzer()
    }

    fun resumeAnalysis() {
        startAnalisis()
    }
    fun release() {
        try {
            controller.clearImageAnalysisAnalyzer()
            controller.unbind()
            analysisExecutor.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}