package com.task.parenttechnicaltask.viewmodel

import android.opengl.Visibility
import android.view.View
import android.view.View.GONE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.model.repository.WeatherRepository
import com.task.parenttechnicaltask.network.Api
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.ui.wrappers.WeatherDay
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import com.task.parenttechnicaltask.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class WeatherViewModel(weatherRepository: WeatherRepository) : BaseViewModel() {
    private val mutableLiveData =
        SingleLiveEvent<CityWeatherWrapper>()
    private val cachedMutableLiveData =
        SingleLiveEvent<ArrayList<CityWeatherWrapper>>()
    private var weatherRepositoryImpl: WeatherRepository? = null


    init {
        weatherRepositoryImpl = weatherRepository
    }

    fun getForecast(city: String): LiveData<CityWeatherWrapper> {

        coroutineScope.launch(Dispatchers.Main) {
            progressVisibility.value = View.VISIBLE
            var weatherResponse = fetchWeatherByCity(city)
            handleResponse(weatherResponse, city)
            progressVisibility.postValue(View.GONE)
        }
        return mutableLiveData

    }

    suspend fun fetchWeatherByCity(city: String): WeatherResult {
        return withContext(Dispatchers.IO)
        {
            weatherRepositoryImpl?.getForecastData(city)
        }!!
    }

    suspend fun fetchWeatherByLatLong(lat: Float, lon: Float): WeatherResult {
        return withContext(Dispatchers.IO)
        {
            weatherRepositoryImpl!!.getForecastData(lat, lon)
        }!!
    }

    fun getForecast(lat: Float, lon: Float): LiveData<CityWeatherWrapper> {
        coroutineScope.launch(Dispatchers.Main) {
            progressVisibility.value = View.VISIBLE
            var weatherResponse = fetchWeatherByLatLong(lat, lon)
            handleResponse(weatherResponse, weatherResponse.city?.name)
            progressVisibility.postValue(View.GONE)
        }
        return mutableLiveData

    }

    fun cacheWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>): Unit {
        coroutineScope.launch {
            saveData(cityWeatherWrappers)
        }
    }

    private suspend fun saveData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>) {
        withContext(Dispatchers.IO)
        {
            weatherRepositoryImpl!!.saveAllWeatherData(cityWeatherWrappers)
        }
    }

    private suspend fun getCached(): ArrayList<CityWeatherWrapper> {
        return withContext(Dispatchers.IO)
        {
            return@withContext weatherRepositoryImpl!!.getAllWeatherData()
        }
    }

    private suspend fun handleResponse(
        weatherResponse: WeatherResult?,
        city: String?
    ) {
        var cityWeatherWrapper = CityWeatherWrapper()
        withContext(Dispatchers.IO)
        {
            var weatherDays = manageData(weatherResponse)

            cityWeatherWrapper.weatherResult = weatherResponse
            cityWeatherWrapper.cityName = city
            cityWeatherWrapper.weatherDays = weatherDays
        }
        mutableLiveData.value = cityWeatherWrapper
    }

    fun getCachedWeatherData(): SingleLiveEvent<ArrayList<CityWeatherWrapper>> {

        coroutineScope.launch(Dispatchers.Main) {
            progressVisibility.value = View.VISIBLE
            var weatherResponse = getCached()
            cachedMutableLiveData.value = weatherResponse
            progressVisibility.postValue(View.GONE)
        }
        return cachedMutableLiveData

    }

    private fun manageData(data: WeatherResult?): List<WeatherDay> {
        var hashMap: HashMap<String, Double> =
            LinkedHashMap<String, Double>()
        for ((index, value) in data?.theList?.withIndex()!!) {
            var simpleDateformat =
                SimpleDateFormat("E") // the day of the week abbreviated
            var day = simpleDateformat.format(value.dt * 1000)
            var dayInMap = hashMap.get(day)
            if (dayInMap == null)
                dayInMap = 0f.toDouble()
            hashMap.put(day, Math.max(dayInMap, value.main.temp_max))

        }
        var list = arrayListOf<WeatherDay>()
        for ((key, value) in hashMap) {
            var weatherDay = WeatherDay()
            weatherDay.day = key
            weatherDay.temperature = value.toInt()
            if (list.size == 5)
                break
            list.add(weatherDay)
        }
        return list
    }
}

