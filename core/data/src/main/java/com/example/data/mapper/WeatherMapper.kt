package com.example.data.mapper

import com.example.domain.model.Weather
import com.example.network.model.WeatherResponse

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        temp = main.temp,
        type = weather.firstOrNull()?.main ?: ""
    )
}