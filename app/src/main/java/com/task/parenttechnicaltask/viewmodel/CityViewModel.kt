package com.task.parenttechnicaltask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.model.repository.CityRepository
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.ui.wrappers.CityWrapper
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityViewModel(cityRepository: CityRepository) : BaseViewModel() {
    private var cityRepository: CityRepository? = null
    private val mutableLiveData =
        SingleLiveEvent<CityWrapper>()

    init {
        this.cityRepository = cityRepository
    }

    fun getCities(): LiveData<CityWrapper> {
        coroutineScope.launch(Dispatchers.Main) {
            var cities = fetchCities()

        }
        return mutableLiveData
    }

    suspend fun fetchCities(): CityWrapper {
        return withContext(Dispatchers.IO)
        {
            cityRepository!!.getAllCities()
        }!!
    }
}