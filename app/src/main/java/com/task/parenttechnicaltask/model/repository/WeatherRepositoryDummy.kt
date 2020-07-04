package com.tests.newandroid.models

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper

class WeatherRepositoryDummy : WeatherRepository {
    private val mutableLiveData =
        MutableLiveData<CityWeatherWrapper>()
    override fun getForecastMutableLiveData(city : String): MutableLiveData<CityWeatherWrapper> {
//        val books =
//            ArrayList<Book>()
        var result= CityWeatherWrapper()
        mutableLiveData.setValue(result)
        return mutableLiveData
    }

    override fun getForecastMutableLiveData(
        lat: Float,
        lon: Float
    ): MutableLiveData<CityWeatherWrapper> {
        TODO("Not yet implemented")
    }

    override fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>) {
        TODO("Not yet implemented")
    }

    override fun getAllWeatherData(): MutableLiveData<ArrayList<CityWeatherWrapper>> {
        TODO("Not yet implemented")
    }


}