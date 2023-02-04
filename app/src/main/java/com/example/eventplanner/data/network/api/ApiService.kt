package com.example.eventplanner.data.network.api

import com.example.eventplanner.data.network.models.Weather
import com.example.eventplanner.utils.KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?key=$KEY&aqi=yes")
    suspend fun getWeather(
        @Query("q") cityName: String = "yerevan"
    ): Response<Weather>

}