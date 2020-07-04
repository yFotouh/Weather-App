package com.task.parenttechnicaltask.repository

import androidx.lifecycle.LiveData
import com.task.parenttechnicaltask.wrappers.CityWrapper

interface CityRepository {
   fun getAllCities() : LiveData<CityWrapper>
}