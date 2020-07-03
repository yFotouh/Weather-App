package com.tests.newandroid.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.network.Api
import com.task.parenttechnicaltask.utils.AppPrefs
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.wrappers.WeatherDay
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class WeatherRepositoryCoImpl : WeatherRepository {
    private val parentJob = Job()
    private val mutableLiveData =
        SingleLiveEvent<CityWeatherWrapper>()
    private val cachedMutableLiveData =
        SingleLiveEvent<ArrayList<CityWeatherWrapper>>()

    // 1
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            //2
            coroutineScope.launch(Dispatchers.Main) {
                //3
                Log.e("error", "error");
//                errorMessage.visibility = View.VISIBLE
//                errorMessage.text = getString(R.string.error_message)
            }

            GlobalScope.launch {
                println("Caught $throwable")
            }
        }

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob +
                coroutineExceptionHandler
    )


    override fun getForecastMutableLiveData(city: String): SingleLiveEvent<CityWeatherWrapper> {

        coroutineScope.launch(Dispatchers.Main) {
            var weatherResponse = fetchWeatherByCity(city)
            handleResponse(weatherResponse, city)
        }
        return mutableLiveData
    }

    private suspend fun WeatherRepositoryCoImpl.handleResponse(
        weatherResponse: WeatherResult,
        city: String?
    ) {
        var weatherDays = manageData(weatherResponse)
        var cityWeatherWrapper = CityWeatherWrapper()
        cityWeatherWrapper.weatherResult = weatherResponse
        cityWeatherWrapper.cityName = city
        cityWeatherWrapper.weatherDays = weatherDays
        mutableLiveData.value = cityWeatherWrapper
    }

    override fun getForecastMutableLiveData(
        lat: Float,
        lon: Float
    ): MutableLiveData<CityWeatherWrapper> {
        coroutineScope.launch(Dispatchers.Main) {
            var weatherResponse = fetchWeatherByCoOrdniate(lat, lon)
            handleResponse(weatherResponse, weatherResponse.city?.name)
        }
        return mutableLiveData
    }

    override fun saveAllWeatherData(cityWeatherWrappers: ArrayList<CityWeatherWrapper>) {
        coroutineScope.launch { AppPrefs.get().cacheWeatherData(cityWeatherWrappers) }
    }

    override fun getAllWeatherData(): SingleLiveEvent<ArrayList<CityWeatherWrapper>> {

        coroutineScope.launch {
            var data = getCachedData()
            cachedMutableLiveData.value = data
        }
        return cachedMutableLiveData
    }

    suspend fun fetchWeatherByCity(city: String): WeatherResult {
        return withContext(Dispatchers.IO)
        {
            Api.getApiClientKt().get5Days(city)
        }
    }

    suspend fun fetchWeatherByCoOrdniate(lat: Float, lon: Float): WeatherResult {
        return withContext(Dispatchers.IO)
        {
            Api.getApiClientKt().get5DaysByLocation(lat, lon)
        }
    }

    suspend fun getCachedData(): ArrayList<CityWeatherWrapper>? {
        return withContext(Dispatchers.IO)
        {
            var data = AppPrefs.get().cacheWeatherData
            if (data == null)
                data = ArrayList<CityWeatherWrapper>()
            return@withContext data
        }
    }

    suspend fun manageData(data: WeatherResult): List<WeatherDay> {
        return withContext(Dispatchers.IO)
        {
            var hashMap: HashMap<String, ArrayList<Double>> =
                LinkedHashMap<String, ArrayList<Double>>()
            for ((index, value) in data.theList.withIndex()) {
                var simpleDateformat =
                    SimpleDateFormat("E") // the day of the week abbreviated
                var day = simpleDateformat.format(value.dt * 1000)
                var dayInMap = hashMap.get(day)
                if (dayInMap == null)
                    dayInMap = ArrayList<Double>()
                dayInMap.add(value.main.temp_max)
                hashMap.put(day, dayInMap)

            }
            var list = arrayListOf<WeatherDay>()
            for ((key, value) in hashMap) {
                var weatherDay = WeatherDay()
                weatherDay.day = key
                weatherDay.temperature = value.average().toInt() - 273.15f.toInt()
                list.add(weatherDay)
            }
            return@withContext list
        }


    }
}