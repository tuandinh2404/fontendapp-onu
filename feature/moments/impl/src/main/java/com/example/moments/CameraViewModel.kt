package com.example.moments

import android.app.Application
import android.graphics.Bitmap
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.coroutine.Dispatcher
import com.example.common.coroutine.OnuDispatchers
import com.example.domain.model.Weather
import com.example.domain.usecase.GetWeatherUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


data class LocationInfo(
    val district: String?,
    val city: String?,
    val country: String?,
    val countryCode: String?
)

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val context: Application,
    private val getWeatherUseCase: GetWeatherUseCase,
    @Dispatcher(OnuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var isOpenedPreview  by mutableStateOf(false)
        private set

    var isCaptured by mutableStateOf(false)
        private set

    var capturedBitmap by mutableStateOf<Bitmap?>(null)
        private set
    var capturedPressScale by mutableStateOf(1f)
        private set

    var isProcessingCapture by mutableStateOf(false)
    private set



    private val _currentTime = MutableStateFlow("")
    val currentTime = _currentTime.asStateFlow()

    init {
        startClock()
    }
    private fun startClock() {
        viewModelScope.launch {
            val formatter = SimpleDateFormat(
                "H:mm",
                Locale.getDefault()
            )
            while (true) {
                val currentTime = formatter.format(Date())
                _currentTime.value = currentTime
                delay(1000)
            }
        }
    }


    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    fun onCapture(pressScale:Float) {
        isCaptured = true
        isProcessingCapture = true
        capturedPressScale = pressScale
    }


    fun onPhotoTaken(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.Main) {
            capturedBitmap = bitmap
            delay(300)
            isProcessingCapture = false
            isCaptured = false
            isOpenedPreview = true
            fetchCurrentLocation()
        }
    }

    fun closePreview() {
        capturedBitmap = null
        isOpenedPreview = false
        isCaptured = false
        isProcessingCapture = false
        capturedPressScale = 1f
    }

    private val _location =
        MutableStateFlow<LocationInfo?>(null)

    val location = _location.asStateFlow()

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun fetchCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    viewModelScope.launch(ioDispatcher) {
                        _location.value =
                            getLocationInfo(
                                it.latitude,
                                it.longitude
                            )
                        fetchWeather(
                            it.latitude,
                            it.longitude
                        )
                    }
                }
            }
    }

    fun getLocationInfo(
        latitude: Double,
        longitude: Double
    ): LocationInfo {
        return try {

            val geocoder = Geocoder(
                context,
                Locale.getDefault()
            )

            val result = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )

            result?.firstOrNull()?.let {

                LocationInfo(
                    district = it.subLocality,
                    city = it.adminArea,
                    country = it.countryName,
                    countryCode = it.countryCode
                )

            } ?: LocationInfo(
                null,
                null,
                null,
                null
            )

        } catch (e: Exception) {

            Log.e(
                "Location",
                "error: ${e.message}"
            )

            LocationInfo(
                null,
                null,
                null,
                null
            )
        }
    }


    private fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch(ioDispatcher) {

            getWeatherUseCase(lat, lon)
                .onSuccess {
                    _weather.value = it
                }
                .onFailure {
                    Log.e("Weather", it.message ?: "Unknown error")
                }
        }
    }
}