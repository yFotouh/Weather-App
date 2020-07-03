package com.tests.newandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tests.newandroid.models.WeatherRepository

class MainActivityViewModelFactory(var weatherRepository: WeatherRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherRepository::class.java).newInstance(weatherRepository)
    }


}