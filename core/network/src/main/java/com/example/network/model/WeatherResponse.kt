package com.example.network.model

data class WeatherResponse(
    val main: MainDTO,
    val weather: List<WeatherDTO>,
)

data class MainDTO(
    val temp: Float
)

data class WeatherDTO(
    val main:String
)