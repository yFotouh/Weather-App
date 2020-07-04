package com.task.parenttechnicaltask.model.repository

import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.network.Api
import com.task.parenttechnicaltask.model.repository.BaseRepository
import com.task.parenttechnicaltask.utils.AppPrefs
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.ui.wrappers.WeatherDay
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class WeatherRepositoryImpl : BaseRepository(), WeatherRepository {


    override suspend fun getForecastData(city: String): WeatherResult {
        return Api.getApiClientKt().get5Days(city)
    }


    override suspend fun getForecastData(
        lat: Float,
        lon: Float
    ): WeatherResult {
        return Api.getApiClientKt().get5DaysByLocation(lat, lon)
    }

    override fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>) {
        AppPrefs.get().cacheWeatherData(cityWeatherWrappers)
    }

    override fun getAllWeatherData(): ArrayList<CityWeatherWrapper> {
        var data = AppPrefs.get().cacheWeatherData
        if (data == null)
            data = ArrayList<CityWeatherWrapper>()
        return data
    }


}