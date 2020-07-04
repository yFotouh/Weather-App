package com.tests.newandroid.models

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.utils.SingleLiveEvent

interface WeatherRepository {
    suspend fun getForecastData(city: String): WeatherResult
    suspend fun getForecastData(lat: Float, lon: Float): WeatherResult
    fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>)
    fun getAllWeatherData(): ArrayList<CityWeatherWrapper>
}