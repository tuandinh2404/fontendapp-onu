package com.example.moments

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Weather
import com.example.domain.usecase.GetWeatherUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val getWeatherUseCase: GetWeatherUseCase
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

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    fun onCapture(pressScale:Float) {
        isCaptured = true
        isProcessingCapture = true
        capturedPressScale = pressScale
    }


    fun onPhotoTaken(bitmap: Bitmap) {
        capturedBitmap = bitmap
        viewModelScope.launch {
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
                Log.d("Location", "location: $location") // 👈 null hay có?
                location?.let {
                    viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {

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