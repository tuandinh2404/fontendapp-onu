package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.domain.model.Weather
import com.example.domain.repository.WeatherRepository
import com.example.network.datasource.WeatherRemoteDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remote: WeatherRemoteDataSource
): WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Result<Weather> {
        return try {
            Result.success(
                remote.getWeather(lat, lon).toDomain()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}