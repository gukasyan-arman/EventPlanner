package com.example.eventplanner.data.network

import com.example.eventplanner.data.network.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeather(cityName: String) = apiService.getWeather(cityName)
}