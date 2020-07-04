package com.tests.newandroid.models

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper

interface WeatherRepository {
    fun getForecastMutableLiveData(city: String): MutableLiveData<CityWeatherWrapper>
    fun getForecastMutableLiveData(lat: Float, lon: Float): MutableLiveData<CityWeatherWrapper>
    fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>)
    fun getAllWeatherData(): MutableLiveData<ArrayList<CityWeatherWrapper>>
}