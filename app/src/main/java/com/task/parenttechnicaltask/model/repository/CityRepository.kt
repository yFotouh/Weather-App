package com.task.parenttechnicaltask.model.repository

import androidx.lifecycle.LiveData
import com.task.parenttechnicaltask.ui.wrappers.CityWrapper

interface CityRepository {
   fun getAllCities() : LiveData<CityWrapper>
}