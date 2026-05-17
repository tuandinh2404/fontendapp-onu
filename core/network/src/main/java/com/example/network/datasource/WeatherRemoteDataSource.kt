package com.example.network.datasource

import com.example.network.model.WeatherResponse
import com.example.network.retrofit.WeatherApi
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val api: WeatherApi,
    @Named("weather_api_key") private val apiKey: String
) {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getWeather(lat, lon, apiKey)
    }
}