package com.example.moments.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.SurfaceTexture
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
import android.util.Size
import android.view.Surface
import android.view.TextureView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraController(private val context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        logAllCameras()
    }

    private var cameraDevice: CameraDevice? = null
    private var captureSession: CameraCaptureSession? = null
    private var imageReader: ImageReader? = null

    private var previewSurface: Surface? = null

    private var backMainCameraId: String? = null
    private var backUltraWideCameraId: String? = null
    private var frontCameraId: String? = null

    private val currentFacing get() = if (_isFrontCamera.value)
        CameraCharacteristics.LENS_FACING_FRONT
    else CameraCharacteristics.LENS_FACING_BACK


    private val _isFrontCamera = MutableStateFlow(true)
    val isFrontCamera = _isFrontCamera.asStateFlow()

    private var isFlashOn = false

    @Volatile private var isReleased = false

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

        val previewSize = getPreviewSize()

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

        imageReader = ImageReader.newInstance(2448,3264, ImageFormat.JPEG, 1)

        cameraManager.openCamera(cameraId,
            object: CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                if(isReleased) {
                    camera.close()
                    return
                }
                cameraDevice = camera
                configureTransform(
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
        }, null)
    }
    private fun configureTransform(
        textureView: TextureView,
        previewSize: Size
    ) {
        val viewWidth = textureView.width.toFloat()   // 1080
        val viewHeight = textureView.height.toFloat() // 1440
        val centerX = viewWidth / 2f
        val centerY = viewHeight / 2f

        // Sau rotate 90°: buffer width↔height đổi chỗ
        // buffer thật sau rotate: 2448 wide x 3264 tall
        val bufferWidth = previewSize.height.toFloat()  // 2448
        val bufferHeight = previewSize.width.toFloat()  // 3264

        val scaleX = viewWidth / bufferWidth    // 1080/2448
        val scaleY = viewHeight / bufferHeight  // 1440/3264

        // Lấy scale lớn hơn để fill
        val scale = maxOf(scaleX, scaleY)

        val matrix = Matrix()
        matrix.postScale(
            scale / scaleY,  // compensate
            scale / scaleX,
            centerX,
            centerY
        )
        textureView.setTransform(matrix)
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
                        if(isReleased) {
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

        reader.setOnImageAvailableListener({ r ->
            val image = r.acquireLatestImage() ?: return@setOnImageAvailableListener
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val rawBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            image.close()

            val matrix = Matrix().apply {
                postRotate(sensorOrientation.toFloat())
                if (_isFrontCamera.value) postScale(-1f, 1f)
            }
            val rotated = Bitmap.createBitmap(
                rawBitmap, 0, 0,
                rawBitmap.width, rawBitmap.height,
                matrix, true
            )

            // Crop zoom
            val result = if (zoomRatio <= 1f) rotated else {
                val cropW = (rotated.width / zoomRatio).toInt()
                val cropH = (rotated.height / zoomRatio).toInt()
                val cropX = (rotated.width - cropW) / 2
                val cropY = (rotated.height - cropH) / 2
                Bitmap.createBitmap(rotated, cropX, cropY, cropW, cropH)
            }

            onFinal(result)
        }, null)

        val captureRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).apply {
            addTarget(reader.surface)
            set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            set(
                CaptureRequest.CONTROL_AE_MODE,
                if (isFlashOn) CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH
                else CaptureRequest.CONTROL_AE_MODE_ON
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
                            previewSurface?.let { startPreview(it, cameraId) }
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

    fun logAllCameras() {
        for (id in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
            val focalLengths = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)
            val maxZoom = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)
            Log.d("CameraInfo", "ID: $id | facing: $facing | focal: ${focalLengths?.toList()} | maxZoom: $maxZoom")
        }
    }

    fun getPreviewSize(): android.util.Size {
        val cameraId = getCameraId(currentFacing)
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        val streamMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        // Lấy preview size phù hợp với view 3:4
        val sizes = streamMap?.getOutputSizes(SurfaceTexture::class.java) ?: return android.util.Size(1080, 1440)

        return sizes
            .filter {
                kotlin.math.abs(
                    (it.width.toFloat() / it.height.toFloat()) - (4f / 3f)
                ) < 0.01f
            }
            .filter { it.width <= 1920 }
            .maxByOrNull { it.width * it.height }
            ?: android.util.Size(1080, 1440)
    }

}