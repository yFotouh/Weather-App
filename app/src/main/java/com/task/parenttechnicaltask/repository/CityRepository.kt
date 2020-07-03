package com.task.parenttechnicaltask.repository

import com.task.parenttechnicaltask.wrappers.CityWrapper

interface CityRepository {
   fun getAllCities() : CityWrapper
}