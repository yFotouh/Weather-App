package com.task.parenttechnicaltask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.parenttechnicaltask.model.repository.CityRepository
import com.task.parenttechnicaltask.ui.wrappers.CityWrapper

class CityViewModel(cityRepository: CityRepository) : ViewModel() {
    private var cityRepository: CityRepository? = null

    init {
        this.cityRepository = cityRepository
    }

    fun getCities(): LiveData<CityWrapper> {
        return cityRepository!!.getAllCities()
    }
}