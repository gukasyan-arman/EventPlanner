package com.example.eventplanner.data.network.api

import com.example.eventplanner.data.network.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?key=4b10c3c519ed41ce97b93804231101&q=Moscow&aqi=yes")
    suspend fun getWeather(
        @Query("q") cityName: String = "yerevan"
    ): Response<Weather>

}