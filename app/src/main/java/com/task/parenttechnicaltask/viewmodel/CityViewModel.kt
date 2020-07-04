package com.task.parenttechnicaltask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.parenttechnicaltask.repository.CityRepository
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.wrappers.CityWrapper
import com.tests.newandroid.models.WeatherRepository

class CityViewModel(cityRepository: CityRepository) : ViewModel() {
    private var cityRepository: CityRepository? = null

    init {
        this.cityRepository = cityRepository
    }

    fun getCities(): LiveData<CityWrapper> {
        return cityRepository!!.getAllCities()
    }
}