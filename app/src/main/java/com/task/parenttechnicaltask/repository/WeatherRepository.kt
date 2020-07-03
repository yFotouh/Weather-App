package com.tests.newandroid.models

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper

interface WeatherRepository {
    fun getForecastMutableLiveData(city: String): MutableLiveData<CityWeatherWrapper>
    fun getForecastMutableLiveData(lat: Float, lon: Float): MutableLiveData<CityWeatherWrapper>
    fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>)
    fun getAllWeatherData(): MutableLiveData<ArrayList<CityWeatherWrapper>>
}