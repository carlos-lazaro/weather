package com.me.weather.data.remote.service

import com.me.weather.BuildConfig
import com.me.weather.data.remote.dto.ForecastResponseDto
import com.me.weather.data.remote.dto.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.API_KEY,
    ): Response<WeatherResponseDto>

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.API_KEY,
    ): Response<ForecastResponseDto>
}
