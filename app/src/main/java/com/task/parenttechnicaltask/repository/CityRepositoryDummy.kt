package com.task.parenttechnicaltask.repository

import androidx.lifecycle.LiveData
import com.task.parenttechnicaltask.wrappers.CityWrapper

class CityRepositoryDummy : CityRepository {
    override fun getAllCities(): LiveData<CityWrapper> {
        TODO("Not yet implemented")
    }
}