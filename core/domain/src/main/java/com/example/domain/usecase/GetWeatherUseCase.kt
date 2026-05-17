package com.example.domain.usecase

import com.example.domain.model.Weather
import com.example.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<Weather> {
        return repository.getWeather(lat, lon)
    }
}