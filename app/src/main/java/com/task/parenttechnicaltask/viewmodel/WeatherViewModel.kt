package com.tests.newandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.tests.newandroid.models.WeatherRepository

class WeatherViewModel(weatherRepository: WeatherRepository) : ViewModel() {
    private var weatherRepositoryImpl: WeatherRepository? = null
    val progressVisibility = MutableLiveData<Boolean>()

    init {
        weatherRepositoryImpl = weatherRepository
    }

    fun getForecast(city: String): LiveData<CityWeatherWrapper> {
        return weatherRepositoryImpl!!.getForecastMutableLiveData(city)
    }

    fun getForecast(lat: Float, lon: Float): LiveData<CityWeatherWrapper> {
        return weatherRepositoryImpl!!.getForecastMutableLiveData(lat, lon)
    }

    fun cacheWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>): Unit {
        weatherRepositoryImpl!!.saveAllWeatherData(cityWeatherWrappers)
    }

    fun getCachedWeatherData(): LiveData<ArrayList<CityWeatherWrapper>> {
        return weatherRepositoryImpl!!.getAllWeatherData()
    }
}