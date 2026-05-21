package com.example.moments.camera.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.ImageReader
import android.util.Log
import android.util.Range
import android.view.Surface
import android.view.TextureView
import com.example.moments.camera.capture.CaptureProcessor
import com.example.moments.camera.preview.PreviewSizeSelector
import com.example.moments.camera.preview.PreviewTransform
import com.example.moments.camera.utils.CameraLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraController(private val context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        CameraLogger.logAllCameras(cameraManager)
    }

    private var cameraDevice: CameraDevice? = null
    private var captureSession: CameraCaptureSession? = null
    private var imageReader: ImageReader? = null

    private var previewSurface: Surface? = null

    private var backMainCameraId: String? = null
    private var backUltraWideCameraId: String? = null
    private var frontCameraId: String? = null

    private val currentFacing
        get() = if (_isFrontCamera.value)
            CameraCharacteristics.LENS_FACING_FRONT
        else CameraCharacteristics.LENS_FACING_BACK


    private val _isFrontCamera = MutableStateFlow(true)
    val isFrontCamera = _isFrontCamera.asStateFlow()

    private var isFlashOn = false

    @Volatile
    private var isReleased = false

    private fun getCameraId(facing: Int): String {
        return cameraManager.cameraIdList.first { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.LENS_FACING) == facing
        }
    }

    private fun getSensorOrientation(cameraId: String): Int {
        return cameraManager.getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
    }

    @SuppressLint("MissingPermission")
    fun openCamera(textureView: TextureView) {
        isReleased = false

        val cameraId = getCameraId(currentFacing)
        val characteristics =
            cameraManager.getCameraCharacteristics(cameraId)

        val previewSize = PreviewSizeSelector.select(
            characteristics
        )

        Log.d(
            "CameraPreview",
            """
        previewSize = ${previewSize.width} x ${previewSize.height}
        textureView = ${textureView.width} x ${textureView.height}
        """.trimIndent()
        )

        val texture = textureView.surfaceTexture!!

        texture.setDefaultBufferSize(
            previewSize.width,
            previewSize.height
        )

        val surface = Surface(texture)

        previewSurface = surface

        imageReader = ImageReader.newInstance(2448, 3264, ImageFormat.JPEG, 1)

        cameraManager.openCamera(
            cameraId,
            object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    if (isReleased) {
                        camera.close()
                        return
                    }
                    cameraDevice = camera

                    PreviewTransform.apply(
                        textureView,
                        previewSize
                    )
                    startPreview(surface, cameraId)
                }

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                    cameraDevice = null
                }

                override fun onError(camera: CameraDevice, p1: Int) {
                    camera.close()
                    cameraDevice = null
                }
            }, null
        )
    }

    private fun getSupportFpsRange(cameraId: String): Range<Int> {
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        val fpsRanges = characteristics.get(
            CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES
        ) ?: return Range(30, 30)

        return fpsRanges.firstOrNull { it.upper == 60 }
            ?: fpsRanges.maxByOrNull { it.upper }
            ?: Range(30, 30)
    }

    private fun startPreview(surface: Surface, cameraId: String) {
        val camera = cameraDevice ?: return
        val reader = imageReader ?: return

        try {
            val previewRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                addTarget(surface)

                set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )

                set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)
                set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, getSupportFpsRange(cameraId))
                set(
                    CaptureRequest.NOISE_REDUCTION_MODE,
                    CaptureRequest.NOISE_REDUCTION_MODE_HIGH_QUALITY
                )
                set(CaptureRequest.EDGE_MODE, CaptureRequest.EDGE_MODE_HIGH_QUALITY)
                set(CaptureRequest.SHADING_MODE, CaptureRequest.SHADING_MODE_HIGH_QUALITY)
                set(CaptureRequest.HOT_PIXEL_MODE, CaptureRequest.HOT_PIXEL_MODE_HIGH_QUALITY)
                set(
                    CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE,
                    CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY
                )

            }


            camera.createCaptureSession(
                listOf(surface, reader.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        if (isReleased) {
                            session.close()
                            return
                        }
                        captureSession = session
                        try {
                            session.setRepeatingRequest(previewRequest.build(), null, null)
                        } catch (e: CameraAccessException) {
                            Log.w("CameraController", "setRepeatingRequest failed: ${e.message}")
                        } catch (e: IllegalStateException) {
                            Log.w("CameraController", "session already closed")
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {}
                }, null
            )
        } catch (e: CameraAccessException) {
            Log.w("CameraController", "startPreview failed: ${e.message}")
        } catch (e: IllegalStateException) {
            Log.w("CameraController", "startPreview: camera already closed")
        }
    }

    fun capture(
        zoomRatio: Float = 1f,
        onFinal: (Bitmap?) -> Unit
    ) {
        Log.d("CropZoom", "Zoom ratio: $zoomRatio")
        val camera = cameraDevice ?: return onFinal(null)
        val session = captureSession ?: return onFinal(null)
        val reader = imageReader ?: return onFinal(null)

        val cameraId = getCameraId(currentFacing)
        val sensorOrientation = getSensorOrientation(cameraId)

        reader.setOnImageAvailableListener(
            { r ->
                val image = r.acquireLatestImage()
                    ?: return@setOnImageAvailableListener
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val rawBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                image.close()

                // Crop zoom
                val result =
                    CaptureProcessor.process(
                        rawBitmap = rawBitmap,
                        sensorOrientation = sensorOrientation,
                        isFrontCamera = _isFrontCamera.value,
                        zoomRatio = zoomRatio
                    )

                onFinal(result)
            }, null
        )

        val captureRequest =
            camera.createCaptureRequest(
                CameraDevice.TEMPLATE_ZERO_SHUTTER_LAG
            ).apply {
                addTarget(reader.surface)
                set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest
                        .CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )
                set(
                    CaptureRequest.CONTROL_AE_MODE,
                    if (isFlashOn)
                        CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH
                    else
                        CaptureRequest.CONTROL_AE_MODE_ON
                )
            }

        try {
            session.capture(
                captureRequest.build(),
                object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                        session: CameraCaptureSession,
                        request: CaptureRequest,
                        result: TotalCaptureResult
                    ) {
                        // Restart preview sau khi chụp
                        if (!isReleased) {
                            previewSurface?.let {
                                startPreview(it, cameraId)
                            }
                        }
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            Log.w("CameraController", "capture failed: ${e.message}")
            onFinal(null)
        } catch (e: IllegalStateException) {
            Log.w("CameraController", "capture: session already closed")
            onFinal(null)
        }
    }


    fun flipCamera(textureView: TextureView) {
        _isFrontCamera.value = !_isFrontCamera.value
        release()
        openCamera(textureView)
    }

    fun toggleFlash() {
        isFlashOn = !isFlashOn
    }

    fun release() {
        isReleased = true
        try {
            captureSession?.close()
            captureSession = null
            cameraDevice?.close()
            cameraDevice = null
            imageReader?.close()
            imageReader = null
            previewSurface = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getMaxZoom(): Float {
        val cameraId = getCameraId(currentFacing)
        return cameraManager.getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM) ?: 4f
    }
}