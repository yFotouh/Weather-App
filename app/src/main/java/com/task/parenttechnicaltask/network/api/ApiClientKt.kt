package com.tests.newandroid.network.api

import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClientKt {
    @GET("forecast")
    suspend fun get5Days(@Query("q") title: String): WeatherResult

    @GET("forecast")
    suspend fun get5DaysByLocation(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float
    ): WeatherResult
}