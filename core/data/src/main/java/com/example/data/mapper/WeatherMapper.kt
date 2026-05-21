package com.example.data.mapper

import com.example.domain.model.Weather
import com.example.network.model.WeatherResponse
import kotlin.math.roundToInt

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        temp = main.temp.roundToInt(),
        type = weather.firstOrNull()?.main ?: ""
    )
}