package com.tests.newandroid.models

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper

class WeatherRepositoryDummy : WeatherRepository {

    override suspend fun getForecastData(city: String): WeatherResult {
        TODO("Not yet implemented")
    }

    override suspend fun getForecastData(lat: Float, lon: Float): WeatherResult {
        TODO("Not yet implemented")
    }

    override fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>) {
        TODO("Not yet implemented")
    }

    override fun getAllWeatherData(): ArrayList<CityWeatherWrapper> {
        TODO("Not yet implemented")
    }


}