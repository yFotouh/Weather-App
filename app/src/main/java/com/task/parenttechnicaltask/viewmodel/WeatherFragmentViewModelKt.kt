package com.tests.newandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper
import com.tests.newandroid.models.WeatherRepository

class WeatherFragmentViewModelKt(weatherRepository: WeatherRepository) : ViewModel() {
    private var weatherRepositoryImpl: WeatherRepository? = null

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